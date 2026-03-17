package admin.panel.dto.contact;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactTopicOptionDTO {

    private Long id;
    private String label;
    private String value;
    private Integer sortOrder;
    private Boolean active;
}