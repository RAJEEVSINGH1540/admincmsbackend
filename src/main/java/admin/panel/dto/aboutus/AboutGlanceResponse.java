package admin.panel.dto.aboutus;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboutGlanceResponse {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}