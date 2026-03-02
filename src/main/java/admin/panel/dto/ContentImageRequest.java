package admin.panel.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContentImageRequest {
    private String image;
    private String paragraph;
    private String heading2;
    private String paragraph2;
}