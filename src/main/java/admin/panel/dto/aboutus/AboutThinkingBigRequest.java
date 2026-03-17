package admin.panel.dto.aboutus;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboutThinkingBigRequest {
    private String title;
    private List<ParagraphRequest> paragraphs;
}