package admin.panel.dto.vendorsuplier;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorFormFieldDTO {

    private Long id;
    private String fieldName;
    private String fieldLabel;
    private String fieldType;
    private String placeholder;
    private Boolean isRequired;
    private Integer sortOrder;
    private Boolean isActive;
}