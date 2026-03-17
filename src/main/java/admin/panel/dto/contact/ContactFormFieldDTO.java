package admin.panel.dto.contact;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactFormFieldDTO {

    private Long id;
    private String label;
    private String fieldType;
    private String fieldName;
    private String placeholder;
    private Boolean required;
    private Integer sortOrder;
    private Boolean active;
    private Boolean isDefault;
}