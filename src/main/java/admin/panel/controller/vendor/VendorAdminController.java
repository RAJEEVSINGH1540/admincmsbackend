package admin.panel.controller.vendor;

import admin.panel.dto.ApiResponse;
import admin.panel.dto.vendor.*;
import admin.panel.service.vendor.VendorAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor")
@RequiredArgsConstructor
public class VendorAdminController {

    private final VendorAdminService vendorAdminService;

    // ==================== VENDOR USERS MANAGEMENT ====================

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<VendorListResponse>>> getAllVendors() {
        return ResponseEntity.ok(ApiResponse.success(vendorAdminService.getAllVendors()));
    }

    @GetMapping("/users/status/{status}")
    public ResponseEntity<ApiResponse<List<VendorListResponse>>> getVendorsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(ApiResponse.success(vendorAdminService.getVendorsByStatus(status)));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<VendorListResponse>> getVendorById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(vendorAdminService.getVendorById(id)));
    }

    @PutMapping("/users/{id}/verify")
    public ResponseEntity<ApiResponse<VendorListResponse>> verifyOrRejectVendor(
            @PathVariable Long id,
            @RequestBody VendorVerifyRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(vendorAdminService.verifyOrRejectVendor(id, request)));
    }

    @PutMapping("/users/{id}/toggle-active")
    public ResponseEntity<ApiResponse<String>> toggleVendorActive(@PathVariable Long id) {
        vendorAdminService.toggleVendorActive(id);
        return ResponseEntity.ok(ApiResponse.success("Vendor active status toggled"));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<String>> deleteVendor(@PathVariable Long id) {
        vendorAdminService.deleteVendor(id);
        return ResponseEntity.ok(ApiResponse.success("Vendor deleted successfully"));
    }

    // ==================== FORM FIELDS ====================

    @GetMapping("/forms")
    public ResponseEntity<ApiResponse<List<VendorFormFieldResponse>>> getAllFormFields() {
        return ResponseEntity.ok(ApiResponse.success(vendorAdminService.getAllFormFields()));
    }

    @GetMapping("/forms/active")
    public ResponseEntity<ApiResponse<List<VendorFormFieldResponse>>> getActiveFormFields() {
        return ResponseEntity.ok(ApiResponse.success(vendorAdminService.getActiveFormFields()));
    }

    @PostMapping("/forms")
    public ResponseEntity<ApiResponse<VendorFormFieldResponse>> createFormField(
            @Valid @RequestBody VendorFormFieldRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(vendorAdminService.createFormField(request)));
    }

    @PutMapping("/forms/{id}")
    public ResponseEntity<ApiResponse<VendorFormFieldResponse>> updateFormField(
            @PathVariable Long id,
            @Valid @RequestBody VendorFormFieldRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(vendorAdminService.updateFormField(id, request)));
    }

    @DeleteMapping("/forms/{id}")
    public ResponseEntity<ApiResponse<String>> deleteFormField(@PathVariable Long id) {
        vendorAdminService.deleteFormField(id);
        return ResponseEntity.ok(ApiResponse.success("Form field deleted"));
    }

    // ==================== NOTIFICATIONS ====================

    @GetMapping("/notifications")
    public ResponseEntity<ApiResponse<List<AdminNotificationResponse>>> getAllNotifications() {
        return ResponseEntity.ok(ApiResponse.success(vendorAdminService.getAllNotifications()));
    }

    @GetMapping("/notifications/unread")
    public ResponseEntity<ApiResponse<List<AdminNotificationResponse>>> getUnreadNotifications() {
        return ResponseEntity.ok(ApiResponse.success(vendorAdminService.getUnreadNotifications()));
    }

    @PutMapping("/notifications/{id}/read")
    public ResponseEntity<ApiResponse<String>> markNotificationAsRead(@PathVariable Long id) {
        vendorAdminService.markNotificationAsRead(id);
        return ResponseEntity.ok(ApiResponse.success("Notification marked as read"));
    }

    @PutMapping("/notifications/read-all")
    public ResponseEntity<ApiResponse<String>> markAllAsRead() {
        vendorAdminService.markAllNotificationsAsRead();
        return ResponseEntity.ok(ApiResponse.success("All notifications marked as read"));
    }

    @DeleteMapping("/notifications/{id}")
    public ResponseEntity<ApiResponse<String>> deleteNotification(@PathVariable Long id) {
        vendorAdminService.deleteNotification(id);
        return ResponseEntity.ok(ApiResponse.success("Notification deleted"));
    }

    // ==================== STATS ====================

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<VendorStatsResponse>> getStats() {
        return ResponseEntity.ok(ApiResponse.success(vendorAdminService.getStats()));
    }
}