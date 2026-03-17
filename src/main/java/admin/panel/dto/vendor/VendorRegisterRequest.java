package admin.panel.dto.vendor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorRegisterRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    private String organisation;

    private String designation;

    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;

    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 digits")
    private String phoneNumber;

    private String licenseNumber;

    // Dynamic fields from CMS form builder
    private Map<String, String> additionalFields;
}