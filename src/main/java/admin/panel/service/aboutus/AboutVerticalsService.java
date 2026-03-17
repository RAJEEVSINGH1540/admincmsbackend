package admin.panel.service.aboutus;

import admin.panel.dto.aboutus.AboutVerticalsRequest;
import admin.panel.dto.aboutus.AboutVerticalsResponse;
import admin.panel.entity.aboutus.AboutVerticals;
import admin.panel.repository.aboutus.AboutVerticalsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AboutVerticalsService {

    private final AboutVerticalsRepository repository;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    public AboutVerticalsResponse get() {
        AboutVerticals entity = repository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new RuntimeException("About Verticals data not found"));
        return mapToResponse(entity);
    }

    public AboutVerticalsResponse update(AboutVerticalsRequest request) {
        AboutVerticals entity = repository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new RuntimeException("About Verticals data not found"));

        if (request.getTitle() != null) entity.setTitle(request.getTitle());
        if (request.getImage1Alt() != null) entity.setImage1Alt(request.getImage1Alt());
        if (request.getImage2Alt() != null) entity.setImage2Alt(request.getImage2Alt());
        if (request.getImage3Alt() != null) entity.setImage3Alt(request.getImage3Alt());

        return mapToResponse(repository.save(entity));
    }

    public AboutVerticalsResponse uploadImage(int imageNumber, MultipartFile file) {
        if (imageNumber < 1 || imageNumber > 3) {
            throw new IllegalArgumentException("Image number must be 1, 2, or 3");
        }

        AboutVerticals entity = repository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new RuntimeException("About Verticals data not found"));

        String oldImage;
        String newUrl = saveFile(file, "aboutus/verticals");

        switch (imageNumber) {
            case 1 -> {
                oldImage = entity.getImage1Url();
                entity.setImage1Url(newUrl);
            }
            case 2 -> {
                oldImage = entity.getImage2Url();
                entity.setImage2Url(newUrl);
            }
            case 3 -> {
                oldImage = entity.getImage3Url();
                entity.setImage3Url(newUrl);
            }
            default -> throw new IllegalArgumentException("Invalid image number");
        }

        AboutVerticals saved = repository.save(entity);
        deleteOldFile(oldImage);
        return mapToResponse(saved);
    }

    private String saveFile(MultipartFile file, String subfolder) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String filename = UUID.randomUUID() + extension;
            Path directory = Paths.get(uploadDir, subfolder);
            Files.createDirectories(directory);
            Path filePath = directory.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/" + subfolder + "/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file: " + e.getMessage());
        }
    }

    private void deleteOldFile(String fileUrl) {
        if (fileUrl == null || fileUrl.isBlank()) return;
        try {
            String relativePath = fileUrl.replace("/uploads/", "");
            Path filePath = Paths.get(uploadDir, relativePath);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.err.println("Failed to delete old file: " + fileUrl);
        }
    }

    private AboutVerticalsResponse mapToResponse(AboutVerticals entity) {
        return AboutVerticalsResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .image1Url(entity.getImage1Url() != null ? baseUrl + entity.getImage1Url() : null)
                .image1Alt(entity.getImage1Alt())
                .image2Url(entity.getImage2Url() != null ? baseUrl + entity.getImage2Url() : null)
                .image2Alt(entity.getImage2Alt())
                .image3Url(entity.getImage3Url() != null ? baseUrl + entity.getImage3Url() : null)
                .image3Alt(entity.getImage3Alt())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}