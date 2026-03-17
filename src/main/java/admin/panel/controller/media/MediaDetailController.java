package admin.panel.controller.media;

import admin.panel.dto.ApiResponse;
import admin.panel.dto.media.MediaDetailDTO;
import admin.panel.service.media.MediaDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/media-detail")
@RequiredArgsConstructor
@Slf4j
public class MediaDetailController {

    private final MediaDetailService service;
    private final ObjectMapper objectMapper;

    // ==================== GET ALL (CMS - all records) ====================
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<MediaDetailDTO>>> getAllMediaDetails() {
        List<MediaDetailDTO> list = service.getAllMediaDetails();
        return ResponseEntity.ok(ApiResponse.success("All media details fetched", list));
    }

    // ==================== GET ALL ACTIVE (PUBLIC - for listing) ====================
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<MediaDetailDTO>>> getAllActiveMediaDetails() {
        List<MediaDetailDTO> list = service.getAllActiveMediaDetails();
        return ResponseEntity.ok(ApiResponse.success("Active media details fetched", list));
    }

    // ==================== GET BY ID (PUBLIC - with other news sidebar) ====================
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MediaDetailDTO>> getMediaDetailById(@PathVariable Long id) {
        MediaDetailDTO dto = service.getMediaDetailById(id);
        return ResponseEntity.ok(ApiResponse.success("Media detail fetched", dto));
    }

    // ==================== GET BY ID (CMS - for editing) ====================
    @GetMapping("/cms/{id}")
    public ResponseEntity<ApiResponse<MediaDetailDTO>> getMediaDetailByIdForCms(@PathVariable Long id) {
        MediaDetailDTO dto = service.getMediaDetailByIdForCms(id);
        return ResponseEntity.ok(ApiResponse.success("Media detail fetched for CMS", dto));
    }

    // ==================== CREATE ====================
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<MediaDetailDTO>> createMediaDetail(
            @RequestPart("data") String dataJson,
            @RequestPart(value = "bannerImage", required = false) MultipartFile bannerImage,
            @RequestPart(value = "cardImage", required = false) MultipartFile cardImage,
            @RequestPart(value = "contentImage", required = false) MultipartFile contentImage
    ) {
        try {
            MediaDetailDTO dto = objectMapper.readValue(dataJson, MediaDetailDTO.class);
            MediaDetailDTO saved = service.createMediaDetail(dto, bannerImage, cardImage, contentImage);
            return ResponseEntity.ok(ApiResponse.success("Media detail created successfully", saved));
        } catch (Exception e) {
            log.error("Failed to create media detail: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to create: " + e.getMessage()));
        }
    }

    // ==================== UPDATE ====================
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<MediaDetailDTO>> updateMediaDetail(
            @PathVariable Long id,
            @RequestPart("data") String dataJson,
            @RequestPart(value = "bannerImage", required = false) MultipartFile bannerImage,
            @RequestPart(value = "cardImage", required = false) MultipartFile cardImage,
            @RequestPart(value = "contentImage", required = false) MultipartFile contentImage
    ) {
        try {
            MediaDetailDTO dto = objectMapper.readValue(dataJson, MediaDetailDTO.class);
            MediaDetailDTO saved = service.updateMediaDetail(id, dto, bannerImage, cardImage, contentImage);
            return ResponseEntity.ok(ApiResponse.success("Media detail updated successfully", saved));
        } catch (Exception e) {
            log.error("Failed to update media detail: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to update: " + e.getMessage()));
        }
    }

    // ==================== DELETE ====================
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMediaDetail(@PathVariable Long id) {
        service.deleteMediaDetail(id);
        return ResponseEntity.ok(ApiResponse.success("Media detail deleted successfully", null));
    }
}