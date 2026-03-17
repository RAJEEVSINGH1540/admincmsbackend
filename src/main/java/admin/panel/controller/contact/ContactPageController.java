package admin.panel.controller.contact;

import admin.panel.dto.ApiResponse;
import admin.panel.dto.contact.ContactFormFieldDTO;
import admin.panel.dto.contact.ContactPageDTO;
import admin.panel.dto.contact.ContactSubmissionDTO;
import admin.panel.dto.contact.ContactTopicOptionDTO;
import admin.panel.service.contact.ContactPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactPageController {

    private final ContactPageService contactPageService;

    // ======================== PAGE CONTENT ========================

    @GetMapping
    public ResponseEntity<ApiResponse<ContactPageDTO>> getContactPage() {
        return ResponseEntity.ok(ApiResponse.success(contactPageService.getContactPage()));
    }

    @GetMapping("/public")
    public ResponseEntity<ApiResponse<ContactPageDTO>> getPublicContactPage() {
        return ResponseEntity.ok(ApiResponse.success(contactPageService.getPublicContactPage()));
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ContactPageDTO>> updateContactPage(
            @RequestParam(required = false) String corporateOfficeTitle,
            @RequestParam(required = false) String corporateOfficeAddress,
            @RequestParam(required = false) String manufacturingFacilityTitle,
            @RequestParam(required = false) String manufacturingFacilityAddress,
            @RequestParam(required = false) String formHeading,
            @RequestPart(required = false) MultipartFile officeImage,
            @RequestPart(required = false) MultipartFile formBackgroundImage,
            @RequestPart(required = false) MultipartFile mapImage
    ) {
        ContactPageDTO result = contactPageService.updateContactPage(
                corporateOfficeTitle, corporateOfficeAddress,
                manufacturingFacilityTitle, manufacturingFacilityAddress,
                formHeading, officeImage, formBackgroundImage, mapImage
        );
        return ResponseEntity.ok(ApiResponse.success("Contact page updated", result));
    }

    // ======================== FORM FIELDS ========================

    @PostMapping("/fields")
    public ResponseEntity<ApiResponse<ContactFormFieldDTO>> addFormField(
            @RequestBody ContactFormFieldDTO dto
    ) {
        return ResponseEntity.ok(ApiResponse.success("Field added", contactPageService.addFormField(dto)));
    }

    @PutMapping("/fields/{id}")
    public ResponseEntity<ApiResponse<ContactFormFieldDTO>> updateFormField(
            @PathVariable Long id,
            @RequestBody ContactFormFieldDTO dto
    ) {
        return ResponseEntity.ok(ApiResponse.success("Field updated", contactPageService.updateFormField(id, dto)));
    }

    @DeleteMapping("/fields/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFormField(@PathVariable Long id) {
        contactPageService.deleteFormField(id);
        return ResponseEntity.ok(ApiResponse.success("Field deleted", null));
    }

    @PutMapping("/fields/reorder")
    public ResponseEntity<ApiResponse<List<ContactFormFieldDTO>>> reorderFields(
            @RequestBody List<Long> fieldIds
    ) {
        return ResponseEntity.ok(ApiResponse.success("Fields reordered", contactPageService.reorderFormFields(fieldIds)));
    }

    // ======================== TOPIC OPTIONS ========================

    @PostMapping("/topics")
    public ResponseEntity<ApiResponse<ContactTopicOptionDTO>> addTopicOption(
            @RequestBody ContactTopicOptionDTO dto
    ) {
        return ResponseEntity.ok(ApiResponse.success("Topic added", contactPageService.addTopicOption(dto)));
    }

    @PutMapping("/topics/{id}")
    public ResponseEntity<ApiResponse<ContactTopicOptionDTO>> updateTopicOption(
            @PathVariable Long id,
            @RequestBody ContactTopicOptionDTO dto
    ) {
        return ResponseEntity.ok(ApiResponse.success("Topic updated", contactPageService.updateTopicOption(id, dto)));
    }

    @DeleteMapping("/topics/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTopicOption(@PathVariable Long id) {
        contactPageService.deleteTopicOption(id);
        return ResponseEntity.ok(ApiResponse.success("Topic deleted", null));
    }

    // ======================== SUBMISSIONS ========================

    @PostMapping("/submit")
    public ResponseEntity<ApiResponse<ContactSubmissionDTO>> submitForm(
            @RequestBody ContactSubmissionDTO dto
    ) {
        return ResponseEntity.ok(ApiResponse.success("Message sent successfully", contactPageService.submitContactForm(dto)));
    }

    @GetMapping("/submissions")
    public ResponseEntity<ApiResponse<List<ContactSubmissionDTO>>> getSubmissions() {
        return ResponseEntity.ok(ApiResponse.success(contactPageService.getAllSubmissions()));
    }

    @PutMapping("/submissions/{id}/read")
    public ResponseEntity<ApiResponse<ContactSubmissionDTO>> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Marked as read", contactPageService.markAsRead(id)));
    }

    @DeleteMapping("/submissions/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSubmission(@PathVariable Long id) {
        contactPageService.deleteSubmission(id);
        return ResponseEntity.ok(ApiResponse.success("Submission deleted", null));
    }

    @GetMapping("/submissions/unread-count")
    public ResponseEntity<ApiResponse<Long>> getUnreadCount() {
        return ResponseEntity.ok(ApiResponse.success(contactPageService.getUnreadCount()));
    }
}