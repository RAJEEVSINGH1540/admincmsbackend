package admin.panel.service.aboutus;

import admin.panel.dto.aboutus.AboutGlanceRequest;
import admin.panel.dto.aboutus.AboutGlanceResponse;
import admin.panel.entity.aboutus.AboutGlance;
import admin.panel.repository.aboutus.AboutGlanceRepository;
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
public class AboutGlanceService {

    private final AboutGlanceRepository repository;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    public AboutGlanceResponse get() {
        AboutGlance entity = repository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new RuntimeException("About Glance data not found"));
        return mapToResponse(entity);
    }

    public AboutGlanceResponse update(AboutGlanceRequest request) {
        AboutGlance entity = repository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new RuntimeException("About Glance data not found"));

        if (request.getTitle() != null) entity.setTitle(request.getTitle());
        if (request.getDescription() != null) entity.setDescription(request.getDescription());

        return mapToResponse(repository.save(entity));
    }

    public AboutGlanceResponse uploadImage(MultipartFile file) {
        AboutGlance entity = repository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new RuntimeException("About Glance data not found"));

        String oldImage = entity.getImageUrl();
        String newUrl = saveFile(file, "aboutus/glance");
        entity.setImageUrl(newUrl);

        AboutGlance saved = repository.save(entity);
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

    private AboutGlanceResponse mapToResponse(AboutGlance entity) {
        return AboutGlanceResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .imageUrl(entity.getImageUrl() != null ? baseUrl + entity.getImageUrl() : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}