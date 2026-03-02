package admin.panel.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboutPageResponse {

    private BannerData banner;
    private ContentImageData contentImage;
    private FeatureImageData featureImage;
    private List<BulletSectionData> sections;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BannerData {
        private Long id;
        private String image;
        private String heading;
        private String paragraph;
        private String heading2;
        private String paragraph2;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ContentImageData {
        private Long id;
        private String image;
        private String paragraph;
        private String heading2;
        private String paragraph2;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FeatureImageData {
        private Long id;
        private String image;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BulletSectionData {
        private Long id;
        private String sectionKey;
        private String image;
        private String heading;
        private List<String> bullets;
    }
}