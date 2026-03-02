package admin.panel.entity.vission;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vision_pages")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VisionPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hero Section
    @Column(columnDefinition = "TEXT")
    private String heroBadgeText;

    @Column(columnDefinition = "TEXT")
    private String heroHeading1;

    @Column(columnDefinition = "TEXT")
    private String heroHighlightWord;

    @Column(columnDefinition = "TEXT")
    private String heroHeading2;

    @Column(columnDefinition = "TEXT")
    private String heroSubtitle;

    @Column(columnDefinition = "TEXT")
    private String heroScrollText;

    // Vision & Mission Section
    @Column(columnDefinition = "TEXT")
    private String visionLabel;

    @Column(columnDefinition = "TEXT")
    private String visionHeading;

    @Column(columnDefinition = "TEXT")
    private String visionDescription;

    @Column(columnDefinition = "TEXT")
    private String missionLabel;

    @Column(columnDefinition = "TEXT")
    private String missionHeading;

    @Column(columnDefinition = "TEXT")
    private String missionDescription;

    // Core Values Section
    @Column(columnDefinition = "TEXT")
    private String coreValuesBadge;

    @Column(columnDefinition = "TEXT")
    private String coreValuesHeading;

    @Column(columnDefinition = "TEXT")
    private String coreValuesSubtitle;

    // Timeline Section
    @Column(columnDefinition = "TEXT")
    private String timelineBadge;

    @Column(columnDefinition = "TEXT")
    private String timelineHeading;

    @Column(columnDefinition = "TEXT")
    private String timelineSubtitle;

    // Stats Section
    @Column(columnDefinition = "TEXT")
    private String statsBadge;

    @Column(columnDefinition = "TEXT")
    private String statsHeading;

    @Column(columnDefinition = "TEXT")
    private String statsSubtitle;

    // Commitment Section
    @Column(columnDefinition = "TEXT")
    private String commitmentBadge;

    @Column(columnDefinition = "TEXT")
    private String commitmentHeading;

    @Column(columnDefinition = "TEXT")
    private String commitmentSubtitle;

    @Column(columnDefinition = "TEXT")
    private String commitmentQuote;

    @Column(columnDefinition = "TEXT")
    private String commitmentQuoteAuthor;

    // CTA Section
    @Column(columnDefinition = "TEXT")
    private String ctaHeading1;

    @Column(columnDefinition = "TEXT")
    private String ctaHighlightText;

    @Column(columnDefinition = "TEXT")
    private String ctaDescription;

    @Column(columnDefinition = "TEXT")
    private String ctaPrimaryButtonText;

    @Column(columnDefinition = "TEXT")
    private String ctaPrimaryButtonLink;

    @Column(columnDefinition = "TEXT")
    private String ctaSecondaryButtonText;

    @Column(columnDefinition = "TEXT")
    private String ctaSecondaryButtonLink;

    @Column(columnDefinition = "TEXT")
    private String ctaTrustLabel;

    // Relations
    @OneToMany(mappedBy = "visionPage", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    @Builder.Default
    private List<VisionPillar> visionPillars = new ArrayList<>();

    @OneToMany(mappedBy = "visionPage", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    @Builder.Default
    private List<MissionMetric> missionMetrics = new ArrayList<>();

    @OneToMany(mappedBy = "visionPage", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    @Builder.Default
    private List<CoreValue> coreValues = new ArrayList<>();

    @OneToMany(mappedBy = "visionPage", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    @Builder.Default
    private List<TimelineMilestone> milestones = new ArrayList<>();

    @OneToMany(mappedBy = "visionPage", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    @Builder.Default
    private List<VisionStat> stats = new ArrayList<>();

    @OneToMany(mappedBy = "visionPage", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    @Builder.Default
    private List<Commitment> commitments = new ArrayList<>();

    @OneToMany(mappedBy = "visionPage", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    @Builder.Default
    private List<TrustBadge> trustBadges = new ArrayList<>();

    private Boolean isActive;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (isActive == null) isActive = true;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}