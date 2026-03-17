package admin.panel.dto.aboutus;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboutThinkingBigCmsResponse {
    private Long id;
    private String title;
    private List<ParagraphResponse> paragraphs;   // Full objects for CMS
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}