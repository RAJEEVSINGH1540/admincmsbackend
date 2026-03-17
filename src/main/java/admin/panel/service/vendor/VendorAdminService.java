package admin.panel.service.vendor;

import admin.panel.dto.vendor.*;
import admin.panel.entity.vendor.AdminNotification;
import admin.panel.entity.vendor.VendorVerificationStatus;
import admin.panel.entity.vendor.VendorFormField;
import admin.panel.entity.vendor.VendorUser;
import admin.panel.repository.vendor.AdminNotificationRepository;
import admin.panel.repository.vendor.VendorFormFieldRepository;
import admin.panel.repository.vendor.VendorUserRepository;
import admin.panel.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VendorAdminService {

    private final VendorUserRepository vendorUserRepository;
    private final VendorFormFieldRepository formFieldRepository;
    private final AdminNotificationRepository notificationRepository;
    private final EmailService emailService;

    // ==================== VENDOR MANAGEMENT ====================

    @Transactional(readOnly = true)
    public List<VendorListResponse> getAllVendors() {
        return vendorUserRepository.findAllByOrderByCreatedAtDesc()
                .stream().map(this::toVendorListResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VendorListResponse> getVendorsByStatus(String status) {
        VendorVerificationStatus verificationStatus = VendorVerificationStatus.valueOf(status.toUpperCase());
        return vendorUserRepository.findByVerificationStatusOrderByCreatedAtDesc(verificationStatus)
                .stream().map(this::toVendorListResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public VendorListResponse getVendorById(Long id) {
        VendorUser user = vendorUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
        return toVendorListResponse(user);
    }

    @Transactional
    public VendorListResponse verifyOrRejectVendor(Long vendorId, VendorVerifyRequest request) {
        VendorUser user = vendorUserRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        if ("verify".equalsIgnoreCase(request.getAction())) {
            user.setVerificationStatus(VendorVerificationStatus.VERIFIED);
            user.setVerifiedAt(LocalDateTime.now());
            user.setRejectionReason(null);
            vendorUserRepository.save(user);

            // Send verification approved email
            emailService.sendVendorApprovedEmail(user.getEmail(), user.getName());

            // Create notification
            createNotification("VENDOR_VERIFIED",
                    "Vendor Verified",
                    "Vendor " + user.getName() + " (" + user.getEmail() + ") has been verified.",
                    user.getId());

            log.info("Vendor verified: {}", user.getEmail());

        } else if ("reject".equalsIgnoreCase(request.getAction())) {
            user.setVerificationStatus(VendorVerificationStatus.REJECTED);
            user.setRejectionReason(request.getRejectionReason());
            vendorUserRepository.save(user);

            // Send rejection email
            emailService.sendVendorRejectedEmail(user.getEmail(), user.getName(), request.getRejectionReason());

            createNotification("VENDOR_REJECTED",
                    "Vendor Rejected",
                    "Vendor " + user.getName() + " (" + user.getEmail() + ") has been rejected.",
                    user.getId());

            log.info("Vendor rejected: {}", user.getEmail());

        } else {
            throw new RuntimeException("Invalid action. Use 'verify' or 'reject'.");
        }

        return toVendorListResponse(user);
    }

    @Transactional
    public void toggleVendorActive(Long vendorId) {
        VendorUser user = vendorUserRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
        user.setIsActive(!user.getIsActive());
        vendorUserRepository.save(user);
        log.info("Vendor {} active status toggled to: {}", user.getEmail(), user.getIsActive());
    }

    @Transactional
    public void deleteVendor(Long vendorId) {
        if (!vendorUserRepository.existsById(vendorId)) {
            throw new RuntimeException("Vendor not found");
        }
        vendorUserRepository.deleteById(vendorId);
        log.info("Vendor deleted: {}", vendorId);
    }

    // ==================== FORM FIELDS MANAGEMENT ====================

    @Transactional(readOnly = true)
    public List<VendorFormFieldResponse> getAllFormFields() {
        return formFieldRepository.findAllByOrderByDisplayOrderAsc()
                .stream().map(this::toFormFieldResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VendorFormFieldResponse> getActiveFormFields() {
        return formFieldRepository.findByIsActiveTrueOrderByDisplayOrderAsc()
                .stream().map(this::toFormFieldResponse).collect(Collectors.toList());
    }

    @Transactional
    public VendorFormFieldResponse createFormField(VendorFormFieldRequest request) {
        if (formFieldRepository.existsByFieldKey(request.getFieldKey())) {
            throw new RuntimeException("Field key '" + request.getFieldKey() + "' already exists");
        }

        VendorFormField field = VendorFormField.builder()
                .fieldKey(request.getFieldKey().trim().toLowerCase().replaceAll("\\s+", "_"))
                .fieldLabel(request.getFieldLabel().trim())
                .fieldType(request.getFieldType() != null ? request.getFieldType() : "text")
                .placeholder(request.getPlaceholder())
                .options(request.getOptions())
                .isRequired(request.getIsRequired() != null ? request.getIsRequired() : false)
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .displayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0)
                .validationPattern(request.getValidationPattern())
                .validationMessage(request.getValidationMessage())
                .minLength(request.getMinLength())
                .maxLength(request.getMaxLength())
                .build();

        VendorFormField saved = formFieldRepository.save(field);
        log.info("Form field created: {}", saved.getFieldKey());
        return toFormFieldResponse(saved);
    }

    @Transactional
    public VendorFormFieldResponse updateFormField(Long id, VendorFormFieldRequest request) {
        VendorFormField field = formFieldRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Form field not found"));

        // Check key uniqueness if changed
        if (!field.getFieldKey().equals(request.getFieldKey()) &&
                formFieldRepository.existsByFieldKey(request.getFieldKey())) {
            throw new RuntimeException("Field key '" + request.getFieldKey() + "' already exists");
        }

        field.setFieldKey(request.getFieldKey().trim().toLowerCase().replaceAll("\\s+", "_"));
        field.setFieldLabel(request.getFieldLabel().trim());
        field.setFieldType(request.getFieldType() != null ? request.getFieldType() : "text");
        field.setPlaceholder(request.getPlaceholder());
        field.setOptions(request.getOptions());
        field.setIsRequired(request.getIsRequired() != null ? request.getIsRequired() : false);
        field.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        field.setDisplayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0);
        field.setValidationPattern(request.getValidationPattern());
        field.setValidationMessage(request.getValidationMessage());
        field.setMinLength(request.getMinLength());
        field.setMaxLength(request.getMaxLength());

        VendorFormField saved = formFieldRepository.save(field);
        log.info("Form field updated: {}", saved.getFieldKey());
        return toFormFieldResponse(saved);
    }

    @Transactional
    public void deleteFormField(Long id) {
        if (!formFieldRepository.existsById(id)) {
            throw new RuntimeException("Form field not found");
        }
        formFieldRepository.deleteById(id);
        log.info("Form field deleted: {}", id);
    }

    // ==================== NOTIFICATIONS ====================

    @Transactional(readOnly = true)
    public List<AdminNotificationResponse> getAllNotifications() {
        return notificationRepository.findAllByOrderByCreatedAtDesc()
                .stream().map(this::toNotificationResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AdminNotificationResponse> getUnreadNotifications() {
        return notificationRepository.findByIsReadFalseOrderByCreatedAtDesc()
                .stream().map(this::toNotificationResponse).collect(Collectors.toList());
    }

    @Transactional
    public void markNotificationAsRead(Long id) {
        AdminNotification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    @Transactional
    public void markAllNotificationsAsRead() {
        List<AdminNotification> unread = notificationRepository.findByIsReadFalseOrderByCreatedAtDesc();
        unread.forEach(n -> n.setIsRead(true));
        notificationRepository.saveAll(unread);
    }

    @Transactional
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    // ==================== STATS ====================

    @Transactional(readOnly = true)
    public VendorStatsResponse getStats() {
        return VendorStatsResponse.builder()
                .totalVendors(vendorUserRepository.count())
                .pendingVerification(vendorUserRepository.countByVerificationStatus(VendorVerificationStatus.PENDING))
                .verified(vendorUserRepository.countByVerificationStatus(VendorVerificationStatus.VERIFIED))
                .rejected(vendorUserRepository.countByVerificationStatus(VendorVerificationStatus.REJECTED))
                .unreadNotifications(notificationRepository.countByIsReadFalse())
                .build();
    }

    // ==================== HELPERS ====================

    public void createNotification(String type, String title, String message, Long referenceId) {
        AdminNotification notification = AdminNotification.builder()
                .type(type)
                .title(title)
                .message(message)
                .referenceId(referenceId)
                .isRead(false)
                .build();
        notificationRepository.save(notification);
    }

    private VendorListResponse toVendorListResponse(VendorUser user) {
        return VendorListResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .organisation(user.getOrganisation())
                .designation(user.getDesignation())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .licenseNumber(user.getLicenseNumber())
                .role(user.getRole().name())
                .isEmailVerified(user.getIsEmailVerified())
                .verificationStatus(user.getVerificationStatus().name())
                .rejectionReason(user.getRejectionReason())
                .isActive(user.getIsActive())
                .additionalFields(user.getAdditionalFields())
                .createdAt(user.getCreatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .verifiedAt(user.getVerifiedAt())
                .build();
    }

    private VendorFormFieldResponse toFormFieldResponse(VendorFormField field) {
        return VendorFormFieldResponse.builder()
                .id(field.getId())
                .fieldKey(field.getFieldKey())
                .fieldLabel(field.getFieldLabel())
                .fieldType(field.getFieldType())
                .placeholder(field.getPlaceholder())
                .options(field.getOptions())
                .isRequired(field.getIsRequired())
                .isActive(field.getIsActive())
                .displayOrder(field.getDisplayOrder())
                .validationPattern(field.getValidationPattern())
                .validationMessage(field.getValidationMessage())
                .minLength(field.getMinLength())
                .maxLength(field.getMaxLength())
                .createdAt(field.getCreatedAt())
                .updatedAt(field.getUpdatedAt())
                .build();
    }

    private AdminNotificationResponse toNotificationResponse(AdminNotification notification) {
        return AdminNotificationResponse.builder()
                .id(notification.getId())
                .type(notification.getType())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .referenceId(notification.getReferenceId())
                .isRead(notification.getIsRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}