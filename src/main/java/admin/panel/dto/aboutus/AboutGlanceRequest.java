package admin.panel.dto.aboutus;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboutGlanceRequest {
    private String title;
    private String description;
}