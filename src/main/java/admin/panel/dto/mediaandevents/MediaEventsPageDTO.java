package admin.panel.dto.mediaandevents;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MediaEventsPageDTO {

    private Long id;

    private String heroBadgeText;
    private String heroHeading1;
    private String heroHighlightWord;
    private String heroSubtitle;
    private String heroScrollText;

    private String pressBadge;
    private String pressHeading;
    private String pressSubtitle;

    private String eventsBadge;
    private String eventsHeading;
    private String eventsSubtitle;

    private String awardsBadge;
    private String awardsHeading;
    private String awardsSubtitle;

    private String coverageBadge;
    private String coverageHeading;
    private String coverageSubtitle;

    private String ctaHeading;
    private String ctaHighlightText;
    private String ctaDescription;
    private String ctaButtonText;
    private String ctaPlaceholder;

    private List<PressReleaseDTO> pressReleases;
    private List<CompanyEventDTO> events;
    private List<AwardDTO> awards;
    private List<MediaCoverageDTO> coverages;

    private Boolean isActive;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class PressReleaseDTO {
        private Long id;
        private String title;
        private String summary;
        private String date;
        private String source;
        private String sourceUrl;
        private String imageUrl;
        private String category;
        private String tagColor;
        private Integer sortOrder;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class CompanyEventDTO {
        private Long id;
        private String title;
        private String description;
        private String date;
        private String time;
        private String location;
        private String imageUrl;
        private String status;
        private String statusColor;
        private String registerUrl;
        private Integer sortOrder;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class AwardDTO {
        private Long id;
        private String title;
        private String description;
        private String year;
        private String organization;
        private String imageUrl;
        private String accentColor;
        private Integer sortOrder;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class MediaCoverageDTO {
        private Long id;
        private String outlet;
        private String logoUrl;
        private String title;
        private String excerpt;
        private String articleUrl;
        private String date;
        private Integer sortOrder;
    }
}