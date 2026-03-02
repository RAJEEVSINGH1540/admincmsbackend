package admin.panel.dto.vission;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VisionPageDTO {

    private Long id;

    // Hero
    private String heroBadgeText;
    private String heroHeading1;
    private String heroHighlightWord;
    private String heroHeading2;
    private String heroSubtitle;
    private String heroScrollText;

    // Vision & Mission
    private String visionLabel;
    private String visionHeading;
    private String visionDescription;
    private String missionLabel;
    private String missionHeading;
    private String missionDescription;

    // Core Values
    private String coreValuesBadge;
    private String coreValuesHeading;
    private String coreValuesSubtitle;

    // Timeline
    private String timelineBadge;
    private String timelineHeading;
    private String timelineSubtitle;

    // Stats
    private String statsBadge;
    private String statsHeading;
    private String statsSubtitle;

    // Commitment
    private String commitmentBadge;
    private String commitmentHeading;
    private String commitmentSubtitle;
    private String commitmentQuote;
    private String commitmentQuoteAuthor;

    // CTA
    private String ctaHeading1;
    private String ctaHighlightText;
    private String ctaDescription;
    private String ctaPrimaryButtonText;
    private String ctaPrimaryButtonLink;
    private String ctaSecondaryButtonText;
    private String ctaSecondaryButtonLink;
    private String ctaTrustLabel;

    // Lists
    private List<VisionPillarDTO> visionPillars;
    private List<MissionMetricDTO> missionMetrics;
    private List<CoreValueDTO> coreValues;
    private List<TimelineMilestoneDTO> milestones;
    private List<VisionStatDTO> stats;
    private List<CommitmentDTO> commitments;
    private List<TrustBadgeDTO> trustBadges;

    private Boolean isActive;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class VisionPillarDTO {
        private Long id;
        private String icon;
        private String text;
        private Integer sortOrder;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class MissionMetricDTO {
        private Long id;
        private String value;
        private String label;
        private String bgColor;
        private String textColor;
        private String borderColor;
        private Integer sortOrder;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class CoreValueDTO {
        private Long id;
        private String title;
        private String description;
        private String iconName;
        private String accentColorFrom;
        private String accentColorTo;
        private String hoverBgColor;
        private String hoverBorderColor;
        private Integer sortOrder;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class TimelineMilestoneDTO {
        private Long id;
        private String year;
        private String title;
        private String description;
        private String icon;
        private String side;
        private Integer sortOrder;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class VisionStatDTO {
        private Long id;
        private Integer value;
        private String suffix;
        private String prefix;
        private String label;
        private String description;
        private String iconName;
        private Integer sortOrder;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class CommitmentDTO {
        private Long id;
        private String title;
        private String description;
        private String iconName;
        private List<String> items;
        private Integer sortOrder;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class TrustBadgeDTO {
        private Long id;
        private String name;
        private Integer sortOrder;
    }
}