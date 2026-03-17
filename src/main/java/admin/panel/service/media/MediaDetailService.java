package admin.panel.service.media;

import admin.panel.dto.media.MediaDetailDTO;
import admin.panel.entity.media.MediaDetail;
import admin.panel.repository.media.MediaDetailRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaDetailService {

    private final MediaDetailRepository repository;
    private final ObjectMapper objectMapper;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    // ==================== GET ALL (CMS) ====================
    public List<MediaDetailDTO> getAllMediaDetails() {
        List<MediaDetail> entities = repository.findAllByOrderByDisplayOrderAscCreatedAtDesc();
        return entities.stream().map(this::toDTOWithoutOtherNews).collect(Collectors.toList());
    }

    // ==================== GET ALL ACTIVE (PUBLIC) ====================
    public List<MediaDetailDTO> getAllActiveMediaDetails() {
        List<MediaDetail> entities = repository.findByIsActiveTrueOrderByDisplayOrderAscCreatedAtDesc();
        return entities.stream().map(this::toDTOWithoutOtherNews).collect(Collectors.toList());
    }

    // ==================== GET BY ID (PUBLIC - with other news) ====================
    public MediaDetailDTO getMediaDetailById(Long id) {
        MediaDetail entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Media detail not found with id: " + id));

        MediaDetailDTO dto = toDTO(entity);

        // Auto-populate "Other News" from other active records (excluding current)
        List<MediaDetail> otherRecords = repository.findByIdNotAndIsActiveTrueOrderByCreatedAtDesc(id);
        List<MediaDetailDTO.OtherNewsItem> otherNews = otherRecords.stream()
                .limit(4)
                .map(record -> MediaDetailDTO.OtherNewsItem.builder()
                        .id(record.getId())
                        .title(record.getArticleTitle())
                        .date(record.getArticleDate())
                        .cardImageUrl(record.getCardImageUrl())
                        .build())
                .collect(Collectors.toList());

        dto.setOtherNews(otherNews);
        return dto;
    }

    // ==================== GET BY ID (CMS - without other news) ====================
    public MediaDetailDTO getMediaDetailByIdForCms(Long id) {
        MediaDetail entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Media detail not found with id: " + id));
        return toDTO(entity);
    }

    // ==================== CREATE ====================
    public MediaDetailDTO createMediaDetail(MediaDetailDTO dto,
                                             MultipartFile bannerImage,
                                             MultipartFile cardImage,
                                             MultipartFile contentImage) {

        MediaDetail entity = new MediaDetail();
        populateEntity(entity, dto, bannerImage, cardImage, contentImage);

        MediaDetail saved = repository.save(entity);
        log.info("Media detail created with id: {}", saved.getId());
        return toDTO(saved);
    }

    // ==================== UPDATE ====================
    public MediaDetailDTO updateMediaDetail(Long id,
                                             MediaDetailDTO dto,
                                             MultipartFile bannerImage,
                                             MultipartFile cardImage,
                                             MultipartFile contentImage) {

        MediaDetail entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Media detail not found with id: " + id));

        populateEntity(entity, dto, bannerImage, cardImage, contentImage);

        MediaDetail saved = repository.save(entity);
        log.info("Media detail updated with id: {}", saved.getId());
        return toDTO(saved);
    }

    // ==================== DELETE ====================
    public void deleteMediaDetail(Long id) {
        MediaDetail entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Media detail not found with id: " + id));

        deleteOldFile(entity.getBannerImageUrl());
        deleteOldFile(entity.getCardImageUrl());
        deleteOldFile(entity.getContentImageUrl());
        repository.delete(entity);
        log.info("Media detail deleted with id: {}", id);
    }

    // ==================== POPULATE ENTITY ====================
    private void populateEntity(MediaDetail entity, MediaDetailDTO dto,
                                 MultipartFile bannerImage,
                                 MultipartFile cardImage,
                                 MultipartFile contentImage) {

        // Handle banner image
        if (bannerImage != null && !bannerImage.isEmpty()) {
            deleteOldFile(entity.getBannerImageUrl());
            String url = saveFile(bannerImage, "media-detail/banner");
            entity.setBannerImageUrl(url);
        } else if (dto.getBannerImageUrl() != null && !dto.getBannerImageUrl().isBlank()
                && !dto.getBannerImageUrl().startsWith("blob:")) {
            entity.setBannerImageUrl(dto.getBannerImageUrl());
        }

        // Handle card image
        if (cardImage != null && !cardImage.isEmpty()) {
            deleteOldFile(entity.getCardImageUrl());
            String url = saveFile(cardImage, "media-detail/card");
            entity.setCardImageUrl(url);
        } else if (dto.getCardImageUrl() != null && !dto.getCardImageUrl().isBlank()
                && !dto.getCardImageUrl().startsWith("blob:")) {
            entity.setCardImageUrl(dto.getCardImageUrl());
        }

        // Handle content image
        if (contentImage != null && !contentImage.isEmpty()) {
            deleteOldFile(entity.getContentImageUrl());
            String url = saveFile(contentImage, "media-detail/content");
            entity.setContentImageUrl(url);
        } else if (dto.getContentImageUrl() != null && !dto.getContentImageUrl().isBlank()
                && !dto.getContentImageUrl().startsWith("blob:")) {
            entity.setContentImageUrl(dto.getContentImageUrl());
        }

        // Text fields
        entity.setCardDate(dto.getCardDate());
        entity.setCardDescription(dto.getCardDescription());
        entity.setArticleTitle(dto.getArticleTitle());
        entity.setArticleDate(dto.getArticleDate());
        entity.setDisplayOrder(dto.getDisplayOrder() != null ? dto.getDisplayOrder() : 0);
        entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);

        // JSON fields
        entity.setArticleParagraphs(toJson(dto.getArticleParagraphs()));
        entity.setImageBelowParagraphs(toJson(dto.getImageBelowParagraphs()));
    }

    // ==================== FILE OPERATIONS ====================
    private String saveFile(MultipartFile file, String subDir) {
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String filename = UUID.randomUUID().toString() + extension;

            Path dirPath = Paths.get(uploadDir, subDir);
            Files.createDirectories(dirPath);

            Path filePath = dirPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/" + subDir + "/" + filename;
        } catch (IOException e) {
            log.error("Failed to save file: {}", e.getMessage());
            throw new RuntimeException("Failed to save file: " + e.getMessage());
        }
    }

    private void deleteOldFile(String fileUrl) {
        if (fileUrl != null && fileUrl.startsWith("/uploads/")) {
            try {
                Path filePath = Paths.get(uploadDir, fileUrl.replace("/uploads/", ""));
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                log.warn("Failed to delete old file: {}", fileUrl);
            }
        }
    }

    // ==================== JSON HELPERS ====================
    private String toJson(Object obj) {
        if (obj == null) return null;
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize to JSON: {}", e.getMessage());
            return null;
        }
    }

    private List<String> fromJsonStringList(String json) {
        if (json == null || json.isBlank()) return new ArrayList<>();
        try {
            return objectMapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            log.error("Failed to parse JSON string list: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    // ==================== MAPPING ====================
    private MediaDetailDTO toDTO(MediaDetail entity) {
        return MediaDetailDTO.builder()
                .id(entity.getId())
                .bannerImageUrl(entity.getBannerImageUrl())
                .cardImageUrl(entity.getCardImageUrl())
                .cardDate(entity.getCardDate())
                .cardDescription(entity.getCardDescription())
                .articleTitle(entity.getArticleTitle())
                .articleParagraphs(fromJsonStringList(entity.getArticleParagraphs()))
                .contentImageUrl(entity.getContentImageUrl())
                .imageBelowParagraphs(fromJsonStringList(entity.getImageBelowParagraphs()))
                .articleDate(entity.getArticleDate())
                .isActive(entity.getIsActive())
                .displayOrder(entity.getDisplayOrder())
                .build();
    }

    private MediaDetailDTO toDTOWithoutOtherNews(MediaDetail entity) {
        return toDTO(entity);
    }
}