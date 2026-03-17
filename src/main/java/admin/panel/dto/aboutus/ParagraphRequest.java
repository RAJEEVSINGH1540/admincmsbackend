package admin.panel.dto.aboutus;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParagraphRequest {
    private Long id;        // null for new, existing id for update
    private String content;
    private Integer sortOrder;
}