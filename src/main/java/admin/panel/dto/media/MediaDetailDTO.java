package admin.panel.dto.media;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaDetailDTO {

    private Long id;

    // Banner Section
    private String bannerImageUrl;
    private String cardImageUrl;
    private String cardDate;
    private String cardDescription;

    // Article Section
    private String articleTitle;
    private List<String> articleParagraphs;
    private String contentImageUrl;
    private List<String> imageBelowParagraphs;
    private String articleDate;

    // Other News (auto-populated from other records)
    private List<OtherNewsItem> otherNews;

    private Boolean isActive;
    private Integer displayOrder;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OtherNewsItem {
        private Long id;
        private String title;
        private String date;
        private String cardImageUrl;
    }
}