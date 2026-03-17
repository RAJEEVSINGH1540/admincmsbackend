package admin.panel.dto.aboutus;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboutVerticalsRequest {
    private String title;
    private String image1Alt;
    private String image2Alt;
    private String image3Alt;
}