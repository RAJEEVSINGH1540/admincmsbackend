package admin.panel.controller;

import admin.panel.dto.photogallery.GalleryCategoryRequest;
import admin.panel.dto.photogallery.GalleryCategoryResponse;
import admin.panel.service.photogallery.GalleryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gallery")
public class GalleryController {

    private final GalleryService galleryService;

    public GalleryController(GalleryService galleryService) {
        this.galleryService = galleryService;
    }

    @GetMapping
    public ResponseEntity<List<GalleryCategoryResponse>> getAll() {
        return ResponseEntity.ok(galleryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GalleryCategoryResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(galleryService.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<GalleryCategoryResponse> create(
            @RequestBody GalleryCategoryRequest request) {
        return ResponseEntity.ok(galleryService.createCategory(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GalleryCategoryResponse> update(
            @PathVariable Long id,
            @RequestBody GalleryCategoryRequest request) {
        return ResponseEntity.ok(galleryService.updateCategory(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        galleryService.deleteCategory(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Category deleted successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<GalleryCategoryResponse> addImage(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String imageUrl = body.get("imageUrl");
        return ResponseEntity.ok(galleryService.addImage(id, imageUrl));
    }

    @DeleteMapping("/{id}/image/{index}")
    public ResponseEntity<GalleryCategoryResponse> removeImage(
            @PathVariable Long id,
            @PathVariable int index) {
        return ResponseEntity.ok(galleryService.removeImage(id, index));
    }
}