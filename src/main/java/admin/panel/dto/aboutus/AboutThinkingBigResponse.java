// src/main/java/admin/panel/dto/aboutus/AboutThinkingBigResponse.java
package admin.panel.dto.aboutus;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboutThinkingBigResponse {
    private Long id;
    private String title;
    private List<String> paragraphs;    // Changed from List<ParagraphResponse> to List<String>
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}