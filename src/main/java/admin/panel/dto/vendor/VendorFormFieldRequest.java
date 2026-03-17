package admin.panel.dto.vendor;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorFormFieldRequest {
    @NotBlank(message = "Field key is required")
    private String fieldKey;

    @NotBlank(message = "Field label is required")
    private String fieldLabel;

    private String fieldType = "text";
    private String placeholder;
    private String options;
    private Boolean isRequired = false;
    private Boolean isActive = true;
    private Integer displayOrder = 0;
    private String validationPattern;
    private String validationMessage;
    private Integer minLength;
    private Integer maxLength;
}