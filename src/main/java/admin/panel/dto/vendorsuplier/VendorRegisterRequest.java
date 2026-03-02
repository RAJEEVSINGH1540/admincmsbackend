package admin.panel.dto.vendorsuplier;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorRegisterRequest {

    @NotBlank(message = "Name is required")
    private String name;

    private String organisation;

    private String designation;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;

    private String phoneNumber;

    private String licenseNumber;

    private Map<String, String> additionalFields;
}