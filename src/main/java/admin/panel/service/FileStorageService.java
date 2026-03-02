package admin.panel.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Set;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${app.upload.video-dir:uploads/videos}")
    private String videoUploadDir;

    private Path videoStoragePath;

    private static final Set<String> ALLOWED_VIDEO_TYPES = Set.of(
            "video/mp4",
            "video/webm",
            "video/ogg",
            "video/quicktime",
            "video/x-msvideo",
            "video/x-matroska"
    );

    private static final long MAX_VIDEO_SIZE = 200L * 1024 * 1024;

    @PostConstruct
    public void init() {
        try {
            videoStoragePath = Paths.get(videoUploadDir).toAbsolutePath().normalize();
            Files.createDirectories(videoStoragePath);
            System.out.println("Video upload directory: " + videoStoragePath);
        } catch (IOException e) {
            throw new RuntimeException("Could not create video upload directory: " + videoUploadDir, e);
        }
    }

    public String storeVideo(MultipartFile file) {
        validateVideo(file);

        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String filename = UUID.randomUUID() + extension;

        try {
            Path targetLocation = videoStoragePath.resolve(filename).normalize();

            if (!targetLocation.startsWith(videoStoragePath)) {
                throw new RuntimeException("Invalid file path detected");
            }

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/videos/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Could not store video file: " + e.getMessage(), e);
        }
    }

    public void deleteFile(String fileUrl) {
        if (fileUrl == null || fileUrl.isBlank()) return;

        try {
            String relativePath = fileUrl.startsWith("/") ? fileUrl.substring(1) : fileUrl;
            Path filePath = Paths.get(relativePath).toAbsolutePath().normalize();

            if (Files.exists(filePath)) {
                Files.delete(filePath);
                System.out.println("Deleted file: " + filePath);
            }
        } catch (IOException e) {
            System.err.println("Could not delete file: " + fileUrl + " — " + e.getMessage());
        }
    }

    private void validateVideo(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Video file is empty");
        }

        if (file.getSize() > MAX_VIDEO_SIZE) {
            throw new IllegalArgumentException(
                    "Video file exceeds maximum size of 200MB. Current size: "
                            + (file.getSize() / (1024 * 1024)) + "MB"
            );
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_VIDEO_TYPES.contains(contentType)) {
            throw new IllegalArgumentException(
                    "Invalid video format: " + contentType + ". Allowed: MP4, WebM, OGG, MOV, AVI, MKV"
            );
        }
    }
}