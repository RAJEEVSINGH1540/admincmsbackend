package admin.panel.controller;

import admin.panel.dto.*;
import admin.panel.service.AboutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/about")
@RequiredArgsConstructor
public class AboutController {

    private final AboutService aboutService;

    // ==================== GET ALL DATA ====================
    @GetMapping
    public ResponseEntity<AboutPageResponse> getAboutPage() {
        return ResponseEntity.ok(aboutService.getAboutPageData());
    }

    // ==================== GET ALL SECTIONS ====================
    @GetMapping("/sections/all")
    public ResponseEntity<List<AboutPageResponse.BulletSectionData>> getAllSections() {
        return ResponseEntity.ok(aboutService.getAllSections());
    }

    // ==================== BANNER ====================
    @PutMapping("/banner")
    public ResponseEntity<AboutPageResponse.BannerData> updateBanner(
            @RequestBody BannerRequest request) {
        return ResponseEntity.ok(aboutService.updateBanner(request));
    }

    @DeleteMapping("/banner/{field}")
    public ResponseEntity<Map<String, String>> clearBannerField(
            @PathVariable String field) {
        aboutService.clearBannerField(field);
        return ResponseEntity.ok(Map.of("message", "Banner " + field + " cleared"));
    }

    // ==================== CONTENT IMAGE ====================
    @PutMapping("/content-image")
    public ResponseEntity<AboutPageResponse.ContentImageData> updateContentImage(
            @RequestBody ContentImageRequest request) {
        return ResponseEntity.ok(aboutService.updateContentImage(request));
    }

    @DeleteMapping("/content-image/{field}")
    public ResponseEntity<Map<String, String>> clearContentImageField(
            @PathVariable String field) {
        aboutService.clearContentImageField(field);
        return ResponseEntity.ok(Map.of("message", "Content image " + field + " cleared"));
    }

    // ==================== FEATURE IMAGE ====================
    @PutMapping("/feature-image")
    public ResponseEntity<AboutPageResponse.FeatureImageData> updateFeatureImage(
            @RequestBody FeatureImageRequest request) {
        return ResponseEntity.ok(aboutService.updateFeatureImage(request));
    }

    @DeleteMapping("/feature-image")
    public ResponseEntity<Map<String, String>> clearFeatureImage() {
        aboutService.clearFeatureImage();
        return ResponseEntity.ok(Map.of("message", "Feature image cleared"));
    }

    // ==================== SECTIONS - CRUD ====================
    @PostMapping("/section/create/{sectionKey}")
    public ResponseEntity<AboutPageResponse.BulletSectionData> createSection(
            @PathVariable String sectionKey,
            @RequestBody BulletSectionRequest request) {
        return ResponseEntity.ok(aboutService.createNewSection(sectionKey, request));
    }

    @PutMapping("/section/{sectionKey}")
    public ResponseEntity<AboutPageResponse.BulletSectionData> updateSection(
            @PathVariable String sectionKey,
            @RequestBody BulletSectionRequest request) {
        return ResponseEntity.ok(aboutService.updateBulletSection(sectionKey, request));
    }

    @DeleteMapping("/section/remove/{sectionKey}")
    public ResponseEntity<Map<String, String>> deleteSection(
            @PathVariable String sectionKey) {
        aboutService.deleteSection(sectionKey);
        return ResponseEntity.ok(Map.of("message", "Section " + sectionKey + " deleted"));
    }

    // ==================== SECTIONS - FIELD CLEAR ====================
    @DeleteMapping("/section/{sectionKey}/field/{field}")
    public ResponseEntity<Map<String, String>> clearSectionField(
            @PathVariable String sectionKey,
            @PathVariable String field) {
        aboutService.clearBulletSectionField(sectionKey, field);
        return ResponseEntity.ok(Map.of("message", "Section " + sectionKey + " " + field + " cleared"));
    }

    // ==================== SECTIONS - BULLETS ====================
    @PostMapping("/section/{sectionKey}/bullet")
    public ResponseEntity<Map<String, String>> addBullet(
            @PathVariable String sectionKey,
            @RequestBody Map<String, String> body) {
        aboutService.addBulletPoint(sectionKey, body.get("bullet"));
        return ResponseEntity.ok(Map.of("message", "Bullet added"));
    }

    @DeleteMapping("/section/{sectionKey}/bullet/{index}")
    public ResponseEntity<Map<String, String>> deleteBullet(
            @PathVariable String sectionKey,
            @PathVariable int index) {
        aboutService.deleteBulletPoint(sectionKey, index);
        return ResponseEntity.ok(Map.of("message", "Bullet deleted"));
    }
}