package admin.panel.dto.vendor;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorFormFieldResponse {
    private Long id;
    private String fieldKey;
    private String fieldLabel;
    private String fieldType;
    private String placeholder;
    private String options;
    private Boolean isRequired;
    private Boolean isActive;
    private Integer displayOrder;
    private String validationPattern;
    private String validationMessage;
    private Integer minLength;
    private Integer maxLength;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}