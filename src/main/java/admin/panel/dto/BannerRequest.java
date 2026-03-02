package admin.panel.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BannerRequest {
    private String image;
    private String heading;
    private String paragraph;
    private String heading2;
    private String paragraph2;
}