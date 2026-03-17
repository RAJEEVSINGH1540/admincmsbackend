package admin.panel.dto.vendor;

import lombok.*;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorListResponse {
    private Long id;
    private String name;
    private String organisation;
    private String designation;
    private String email;
    private String phoneNumber;
    private String licenseNumber;
    private String role;
    private Boolean isEmailVerified;
    private String verificationStatus;
    private String rejectionReason;
    private Boolean isActive;
    private Map<String, String> additionalFields;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    private LocalDateTime verifiedAt;
}