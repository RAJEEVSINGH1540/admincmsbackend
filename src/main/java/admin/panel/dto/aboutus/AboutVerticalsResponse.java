package admin.panel.dto.aboutus;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboutVerticalsResponse {
    private Long id;
    private String title;
    private String image1Url;
    private String image1Alt;
    private String image2Url;
    private String image2Alt;
    private String image3Url;
    private String image3Alt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}