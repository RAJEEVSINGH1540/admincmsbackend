package admin.panel.controller;

import admin.panel.dto.contactus.ContactUsPageRequest;
import admin.panel.dto.contactus.ContactUsPageResponse;
import admin.panel.service.contactus.ContactUsPageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contact-page")
@CrossOrigin(origins = "*")
public class ContactUsPageController {

    private final ContactUsPageService service;

    public ContactUsPageController(ContactUsPageService service) {
        this.service = service;
    }

    // ==================== Page CRUD ====================

    @GetMapping
    public ResponseEntity<List<ContactUsPageResponse>> getAllPages() {
        return ResponseEntity.ok(service.getAllPages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactUsPageResponse> getPageById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPageById(id));
    }

    @PostMapping
    public ResponseEntity<ContactUsPageResponse> createPage(@RequestBody ContactUsPageRequest request) {
        return ResponseEntity.ok(service.createPage(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactUsPageResponse> updatePage(@PathVariable Long id,
                                                            @RequestBody ContactUsPageRequest request) {
        return ResponseEntity.ok(service.updatePage(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePage(@PathVariable Long id) {
        service.deletePage(id);
        return ResponseEntity.ok().build();
    }

    // ==================== Hero Image Operations ====================

    @PostMapping("/{id}/hero-image")
    public ResponseEntity<ContactUsPageResponse> addHeroImage(@PathVariable Long id,
                                                              @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(service.addHeroImage(id, body.get("imageUrl")));
    }

    @DeleteMapping("/{id}/hero-image/{index}")
    public ResponseEntity<ContactUsPageResponse> removeHeroImage(@PathVariable Long id,
                                                                 @PathVariable int index) {
        return ResponseEntity.ok(service.removeHeroImage(id, index));
    }

    // ==================== Location Operations ====================

    @PostMapping("/{id}/location")
    public ResponseEntity<ContactUsPageResponse> addLocation(@PathVariable Long id,
                                                             @RequestBody ContactUsPageRequest.LocationRequest location) {
        return ResponseEntity.ok(service.addLocation(id, location));
    }

    @DeleteMapping("/{id}/location/{locationId}")
    public ResponseEntity<ContactUsPageResponse> removeLocation(@PathVariable Long id,
                                                                @PathVariable Long locationId) {
        return ResponseEntity.ok(service.removeLocation(id, locationId));
    }

    // ==================== Topic Option Operations ====================

    @PostMapping("/{id}/topic-option")
    public ResponseEntity<ContactUsPageResponse> addTopicOption(@PathVariable Long id,
                                                                @RequestBody ContactUsPageRequest.TopicOptionRequest topic) {
        return ResponseEntity.ok(service.addTopicOption(id, topic));
    }

    @DeleteMapping("/{id}/topic-option/{optionId}")
    public ResponseEntity<ContactUsPageResponse> removeTopicOption(@PathVariable Long id,
                                                                   @PathVariable Long optionId) {
        return ResponseEntity.ok(service.removeTopicOption(id, optionId));
    }

    // ==================== Form Field Operations ====================

    @PostMapping("/{id}/form-field")
    public ResponseEntity<ContactUsPageResponse> addFormField(@PathVariable Long id,
                                                              @RequestBody ContactUsPageRequest.FormFieldRequest field) {
        return ResponseEntity.ok(service.addFormField(id, field));
    }

    @DeleteMapping("/{id}/form-field/{fieldId}")
    public ResponseEntity<ContactUsPageResponse> removeFormField(@PathVariable Long id,
                                                                 @PathVariable Long fieldId) {
        return ResponseEntity.ok(service.removeFormField(id, fieldId));
    }
}