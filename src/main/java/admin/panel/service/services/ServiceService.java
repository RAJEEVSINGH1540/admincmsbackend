package admin.panel.service.services;

import admin.panel.dto.services.ServiceRequest;
import admin.panel.dto.services.ServiceResponse;
import admin.panel.entity.services.Service;
import admin.panel.repository.services.ServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
public class ServiceService {

    private final ServiceRepository serviceRepository;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Value("${app.base.url:http://localhost:8080}")
    private String baseUrl;

    // ─── GET ALL ──────────────────────────────────────────────
    public List<ServiceResponse> getAllServices() {
        return serviceRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── GET BY ID ────────────────────────────────────────────
    public ServiceResponse getServiceById(Long id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + id));
        return toResponse(service);
    }

    // ─── CREATE ───────────────────────────────────────────────
    public ServiceResponse createService(ServiceRequest request, MultipartFile image) {
        Service service = Service.builder()
                .title(request.getTitle())
                .paragraphs(request.getParagraphs() != null ? request.getParagraphs() : new ArrayList<>())
                .build();

        if (image != null && !image.isEmpty()) {
            String imageUrl = saveImage(image);
            service.setImageUrl(imageUrl);
        }

        Service saved = serviceRepository.save(service);
        log.info("Service created with id: {}", saved.getServiceId());
        return toResponse(saved);
    }

    // ─── UPDATE ───────────────────────────────────────────────
    public ServiceResponse updateService(Long id, ServiceRequest request, MultipartFile image) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + id));

        if (request.getTitle() != null) {
            service.setTitle(request.getTitle());
        }

        if (request.getParagraphs() != null) {
            service.setParagraphs(request.getParagraphs());
        }

        if (image != null && !image.isEmpty()) {
            // Delete old image if exists
            deleteOldImage(service.getImageUrl());
            String imageUrl = saveImage(image);
            service.setImageUrl(imageUrl);
        }

        Service updated = serviceRepository.save(service);
        log.info("Service updated with id: {}", updated.getServiceId());
        return toResponse(updated);
    }

    // ─── DELETE ───────────────────────────────────────────────
    public void deleteService(Long id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + id));

        // Delete associated image
        deleteOldImage(service.getImageUrl());

        serviceRepository.delete(service);
        log.info("Service deleted with id: {}", id);
    }

    // ─── HELPERS ──────────────────────────────────────────────
    private String saveImage(MultipartFile file) {
        try {
            String serviceDir = uploadDir + "/services";
            Path dirPath = Paths.get(serviceDir);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String filename = UUID.randomUUID() + extension;

            Path filePath = dirPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/services/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image: " + e.getMessage());
        }
    }

    private void deleteOldImage(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                String relativePath = imageUrl.startsWith("/") ? imageUrl.substring(1) : imageUrl;
                Path oldFile = Paths.get(relativePath);
                Files.deleteIfExists(oldFile);
            } catch (IOException e) {
                log.warn("Could not delete old image: {}", imageUrl);
            }
        }
    }

    private ServiceResponse toResponse(Service service) {
        return ServiceResponse.builder()
                .serviceId(service.getServiceId())
                .title(service.getTitle())
                .imageUrl(service.getImageUrl())
                .paragraphs(service.getParagraphs())
                .createdAt(service.getCreatedAt())
                .updatedAt(service.getUpdatedAt())
                .build();
    }
}