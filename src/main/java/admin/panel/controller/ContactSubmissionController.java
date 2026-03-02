package admin.panel.controller;

import admin.panel.dto.contactus.ContactSubmissionRequest;
import admin.panel.dto.contactus.ContactSubmissionResponse;
import admin.panel.service.contactus.ContactUsPageService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/contact-submissions")
@CrossOrigin(origins = "*")
public class ContactSubmissionController {

    private final ContactUsPageService service;

    public ContactSubmissionController(ContactUsPageService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ContactSubmissionResponse> submitForm(@RequestBody ContactSubmissionRequest request) {
        return ResponseEntity.ok(service.submitForm(request));
    }

    @GetMapping
    public ResponseEntity<Page<ContactSubmissionResponse>> getSubmissions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(service.getAllSubmissions(page, size));
    }

    @GetMapping("/unread")
    public ResponseEntity<Page<ContactSubmissionResponse>> getUnreadSubmissions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(service.getUnreadSubmissions(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactSubmissionResponse> getSubmissionById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getSubmissionById(id));
    }

    @GetMapping("/unread-count")
    public ResponseEntity<Map<String, Long>> getUnreadCount() {
        return ResponseEntity.ok(Map.of("count", service.getUnreadCount()));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        service.markAsRead(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubmission(@PathVariable Long id) {
        service.deleteSubmission(id);
        return ResponseEntity.ok().build();
    }
}