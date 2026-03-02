package admin.panel.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomePageRequest {

    private String heroHeading;
    private String heroParagraph;
    private String heroVideoUrl;
    private String contentVideoUrl;
    private Boolean isActive;
}