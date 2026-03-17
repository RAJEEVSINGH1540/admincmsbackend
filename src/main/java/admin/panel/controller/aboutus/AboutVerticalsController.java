package admin.panel.controller.aboutus;

import admin.panel.dto.ApiResponse;
import admin.panel.dto.aboutus.AboutVerticalsRequest;
import admin.panel.dto.aboutus.AboutVerticalsResponse;
import admin.panel.service.aboutus.AboutVerticalsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/aboutus/verticals")
@RequiredArgsConstructor
public class AboutVerticalsController {

    private final AboutVerticalsService service;

    @GetMapping
    public ResponseEntity<ApiResponse<AboutVerticalsResponse>> get() {
        return ResponseEntity.ok(ApiResponse.success("Verticals fetched", service.get()));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<AboutVerticalsResponse>> update(@RequestBody AboutVerticalsRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Verticals updated", service.update(request)));
    }

    @PostMapping(value = "/image/{imageNumber}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<AboutVerticalsResponse>> uploadImage(
            @PathVariable int imageNumber, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(ApiResponse.success("Image " + imageNumber + " uploaded", service.uploadImage(imageNumber, file)));
    }
}