package admin.panel.controller.services;

import admin.panel.dto.ApiResponse;
import admin.panel.dto.services.ServiceRequest;
import admin.panel.dto.services.ServiceResponse;
import admin.panel.service.services.ServiceService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceService serviceService;
    private final ObjectMapper objectMapper;

    // ─── GET ALL ──────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<ApiResponse<List<ServiceResponse>>> getAllServices() {
        List<ServiceResponse> services = serviceService.getAllServices();
        return ResponseEntity.ok(ApiResponse.success("Services fetched successfully", services));
    }

    // ─── GET BY ID ────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ServiceResponse>> getServiceById(@PathVariable Long id) {
        ServiceResponse service = serviceService.getServiceById(id);
        return ResponseEntity.ok(ApiResponse.success("Service fetched successfully", service));
    }

    // ─── CREATE ───────────────────────────────────────────────
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ServiceResponse>> createService(
            @RequestPart("title") String title,
            @RequestPart(value = "paragraphs", required = false) String paragraphsJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        List<String> paragraphs = parseParagraphs(paragraphsJson);

        ServiceRequest request = new ServiceRequest();
        request.setTitle(title);
        request.setParagraphs(paragraphs);

        ServiceResponse created = serviceService.createService(request, image);
        return ResponseEntity.ok(ApiResponse.success("Service created successfully", created));
    }

    // ─── UPDATE ───────────────────────────────────────────────
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ServiceResponse>> updateService(
            @PathVariable Long id,
            @RequestPart(value = "title", required = false) String title,
            @RequestPart(value = "paragraphs", required = false) String paragraphsJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        List<String> paragraphs = parseParagraphs(paragraphsJson);

        ServiceRequest request = new ServiceRequest();
        request.setTitle(title);
        request.setParagraphs(paragraphs);

        ServiceResponse updated = serviceService.updateService(id, request, image);
        return ResponseEntity.ok(ApiResponse.success("Service updated successfully", updated));
    }

    // ─── DELETE ───────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
        return ResponseEntity.ok(ApiResponse.success("Service deleted successfully", null));
    }

    // ─── HELPER ───────────────────────────────────────────────
    private List<String> parseParagraphs(String json) {
        if (json == null || json.isEmpty()) {
            return List.of();
        }
        try {
            return objectMapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Invalid paragraphs format");
        }
    }
}