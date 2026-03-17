package admin.panel.dto.vendor;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorProfileResponse {

    private Long id;
    private String name;
    private String organisation;
    private String designation;
    private String email;
    private String phoneNumber;
    private String licenseNumber;
    private String role;
    private Boolean isEmailVerified;
    private Boolean isActive;
    private String verificationStatus;
    private Map<String, String> additionalFields;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
}