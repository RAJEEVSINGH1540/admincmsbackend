package admin.panel.service.homepage;

import admin.panel.dto.HomePageDTO;
import admin.panel.dto.HomePageRequest;
import admin.panel.entity.homepage.HomePage;
import admin.panel.repository.homepage.HomePageRepository;
import admin.panel.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomePageService {

    private final HomePageRepository homePageRepository;
    private final FileStorageService fileStorageService;

    public List<HomePageDTO> getAll() {
        return homePageRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public HomePageDTO getById(Long id) {
        HomePage entity = homePageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Home page not found with id: " + id));
        return toDTO(entity);
    }

    @Transactional
    public HomePageDTO create(HomePageRequest request,
                              MultipartFile heroVideo,
                              MultipartFile contentVideo) {

        HomePage entity = HomePage.builder()
                .heroHeading(clean(request.getHeroHeading()))
                .heroParagraph(clean(request.getHeroParagraph()))
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .build();

        if (heroVideo != null && !heroVideo.isEmpty()) {
            entity.setHeroVideoUrl(fileStorageService.storeVideo(heroVideo));
        }

        if (contentVideo != null && !contentVideo.isEmpty()) {
            entity.setContentVideoUrl(fileStorageService.storeVideo(contentVideo));
        }

        return toDTO(homePageRepository.save(entity));
    }

    @Transactional
    public HomePageDTO update(Long id,
                              HomePageRequest request,
                              MultipartFile heroVideo,
                              MultipartFile contentVideo) {

        HomePage entity = homePageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Home page not found with id: " + id));

        // Update text fields
        if (request.getHeroHeading() != null) {
            entity.setHeroHeading(request.getHeroHeading());
        }
        if (request.getHeroParagraph() != null) {
            entity.setHeroParagraph(request.getHeroParagraph());
        }
        if (request.getIsActive() != null) {
            entity.setIsActive(request.getIsActive());
        }

        // Handle hero video
        if (heroVideo != null && !heroVideo.isEmpty()) {
            deleteOldFile(entity.getHeroVideoUrl());
            entity.setHeroVideoUrl(fileStorageService.storeVideo(heroVideo));
        } else if (request.getHeroVideoUrl() != null) {
            if (request.getHeroVideoUrl().isBlank()) {
                deleteOldFile(entity.getHeroVideoUrl());
                entity.setHeroVideoUrl(null);
            } else {
                entity.setHeroVideoUrl(request.getHeroVideoUrl());
            }
        }

        // Handle content video
        if (contentVideo != null && !contentVideo.isEmpty()) {
            deleteOldFile(entity.getContentVideoUrl());
            entity.setContentVideoUrl(fileStorageService.storeVideo(contentVideo));
        } else if (request.getContentVideoUrl() != null) {
            if (request.getContentVideoUrl().isBlank()) {
                deleteOldFile(entity.getContentVideoUrl());
                entity.setContentVideoUrl(null);
            } else {
                entity.setContentVideoUrl(request.getContentVideoUrl());
            }
        }

        return toDTO(homePageRepository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        HomePage entity = homePageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Home page not found with id: " + id));

        deleteOldFile(entity.getHeroVideoUrl());
        deleteOldFile(entity.getContentVideoUrl());

        homePageRepository.delete(entity);
    }

    @Transactional
    public HomePageDTO clearHeroVideo(Long id) {
        HomePage entity = homePageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Home page not found with id: " + id));

        deleteOldFile(entity.getHeroVideoUrl());
        entity.setHeroVideoUrl(null);

        return toDTO(homePageRepository.save(entity));
    }

    @Transactional
    public HomePageDTO clearContentVideo(Long id) {
        HomePage entity = homePageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Home page not found with id: " + id));

        deleteOldFile(entity.getContentVideoUrl());
        entity.setContentVideoUrl(null);

        return toDTO(homePageRepository.save(entity));
    }

    @Transactional
    public HomePageDTO clearHeroText(Long id, String field) {
        HomePage entity = homePageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Home page not found with id: " + id));

        switch (field.toLowerCase()) {
            case "heading" -> entity.setHeroHeading(null);
            case "paragraph" -> entity.setHeroParagraph(null);
            case "all" -> {
                entity.setHeroHeading(null);
                entity.setHeroParagraph(null);
            }
            default -> throw new IllegalArgumentException("Invalid field: " + field + ". Use: heading, paragraph, all");
        }

        return toDTO(homePageRepository.save(entity));
    }

    // ==================== Helpers ====================

    private void deleteOldFile(String url) {
        if (url != null && !url.isBlank()) {
            fileStorageService.deleteFile(url);
        }
    }

    private String clean(String value) {
        return value != null ? value.trim() : "";
    }

    private HomePageDTO toDTO(HomePage entity) {
        return HomePageDTO.builder()
                .id(entity.getId())
                .heroVideoUrl(entity.getHeroVideoUrl())
                .heroHeading(entity.getHeroHeading())
                .heroParagraph(entity.getHeroParagraph())
                .contentVideoUrl(entity.getContentVideoUrl())
                .isActive(entity.getIsActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}