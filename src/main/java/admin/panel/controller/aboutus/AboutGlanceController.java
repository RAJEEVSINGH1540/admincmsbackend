package admin.panel.controller.aboutus;

import admin.panel.dto.ApiResponse;
import admin.panel.dto.aboutus.AboutGlanceRequest;
import admin.panel.dto.aboutus.AboutGlanceResponse;
import admin.panel.service.aboutus.AboutGlanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/aboutus/glance")
@RequiredArgsConstructor
public class AboutGlanceController {

    private final AboutGlanceService service;

    @GetMapping
    public ResponseEntity<ApiResponse<AboutGlanceResponse>> get() {
        return ResponseEntity.ok(ApiResponse.success("Glance fetched", service.get()));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<AboutGlanceResponse>> update(@RequestBody AboutGlanceRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Glance updated", service.update(request)));
    }

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<AboutGlanceResponse>> uploadImage(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(ApiResponse.success("Glance image uploaded", service.uploadImage(file)));
    }
}