package admin.panel.controller.vendorsuplier;

import admin.panel.dto.ApiResponse;
import admin.panel.dto.vendorsuplier.*;
import admin.panel.service.vendorsuplier.VendorFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vendor")
@RequiredArgsConstructor
public class VendorFormController {

    private final VendorFormService vendorFormService;

    // ==================== FORM CONFIG ====================

    @GetMapping("/forms")
    public ResponseEntity<ApiResponse<List<VendorFormDTO>>> getAllForms() {
        return ResponseEntity.ok(ApiResponse.success(vendorFormService.getAllForms()));
    }

    @GetMapping("/forms/{id}")
    public ResponseEntity<ApiResponse<VendorFormDTO>> getFormById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(vendorFormService.getFormById(id)));
    }

    @PostMapping("/forms")
    public ResponseEntity<ApiResponse<VendorFormDTO>> createForm(@RequestBody VendorFormRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Vendor form created", vendorFormService.createForm(request)));
    }

    @PutMapping("/forms/{id}")
    public ResponseEntity<ApiResponse<VendorFormDTO>> updateForm(
            @PathVariable Long id, @RequestBody VendorFormRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Vendor form updated", vendorFormService.updateForm(id, request)));
    }

    @DeleteMapping("/forms/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteForm(@PathVariable Long id) {
        vendorFormService.deleteForm(id);
        return ResponseEntity.ok(ApiResponse.success("Vendor form deleted", null));
    }

    // ==================== REGISTERED VENDOR USERS (CMS) ====================

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<Page<VendorUserListDTO>>> getAllVendorUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(vendorFormService.getAllVendorUsers(page, size)));
    }

    @GetMapping("/users/unverified")
    public ResponseEntity<ApiResponse<Page<VendorUserListDTO>>> getUnverifiedUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(vendorFormService.getUnverifiedVendorUsers(page, size)));
    }

    @GetMapping("/users/unverified-count")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getUnverifiedCount() {
        return ResponseEntity.ok(ApiResponse.success(Map.of("count", vendorFormService.getUnverifiedCount())));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<VendorUserListDTO>> getVendorUserById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(vendorFormService.getVendorUserById(id)));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteVendorUser(@PathVariable Long id) {
        vendorFormService.deleteVendorUser(id);
        return ResponseEntity.ok(ApiResponse.success("Vendor user deleted", null));
    }

    @PatchMapping("/users/{id}/toggle-active")
    public ResponseEntity<ApiResponse<Void>> toggleActive(@PathVariable Long id) {
        vendorFormService.toggleVendorUserActive(id);
        return ResponseEntity.ok(ApiResponse.success("Status toggled", null));
    }

    // ==================== FORM SUBMISSIONS ====================

    @PostMapping("/submissions")
    public ResponseEntity<ApiResponse<VendorSubmissionDTO>> submitForm(
            @RequestBody VendorSubmissionRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Submitted", vendorFormService.submitForm(request)));
    }

    @GetMapping("/submissions")
    public ResponseEntity<ApiResponse<Page<VendorSubmissionDTO>>> getSubmissions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(vendorFormService.getSubmissions(page, size)));
    }

    @GetMapping("/submissions/unread")
    public ResponseEntity<ApiResponse<Page<VendorSubmissionDTO>>> getUnreadSubmissions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(vendorFormService.getUnreadSubmissions(page, size)));
    }

    @GetMapping("/submissions/unread-count")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getUnreadCount() {
        return ResponseEntity.ok(ApiResponse.success(Map.of("count", vendorFormService.getUnreadCount())));
    }

    @PatchMapping("/submissions/{id}/read")
    public ResponseEntity<ApiResponse<Void>> markAsRead(@PathVariable Long id) {
        vendorFormService.markAsRead(id);
        return ResponseEntity.ok(ApiResponse.success("Marked as read", null));
    }

    @DeleteMapping("/submissions/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSubmission(@PathVariable Long id) {
        vendorFormService.deleteSubmission(id);
        return ResponseEntity.ok(ApiResponse.success("Deleted", null));
    }
}