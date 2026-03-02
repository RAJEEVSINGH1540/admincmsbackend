package admin.panel.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomePageDTO {

    private Long id;
    private String heroVideoUrl;
    private String heroHeading;
    private String heroParagraph;
    private String contentVideoUrl;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}