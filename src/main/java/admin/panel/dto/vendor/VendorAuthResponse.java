package admin.panel.dto.vendor;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorAuthResponse {

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

    // JWT tokens
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn;

    // Dynamic fields
    private Map<String, String> additionalFields;
}