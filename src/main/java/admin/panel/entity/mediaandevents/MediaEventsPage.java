package admin.panel.entity.mediaandevents;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "media_events_pages")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MediaEventsPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hero
    @Column(columnDefinition = "TEXT")
    private String heroBadgeText;
    @Column(columnDefinition = "TEXT")
    private String heroHeading1;
    @Column(columnDefinition = "TEXT")
    private String heroHighlightWord;
    @Column(columnDefinition = "TEXT")
    private String heroSubtitle;
    @Column(columnDefinition = "TEXT")
    private String heroScrollText;

    // Press Section
    @Column(columnDefinition = "TEXT")
    private String pressBadge;
    @Column(columnDefinition = "TEXT")
    private String pressHeading;
    @Column(columnDefinition = "TEXT")
    private String pressSubtitle;

    // Events Section
    @Column(columnDefinition = "TEXT")
    private String eventsBadge;
    @Column(columnDefinition = "TEXT")
    private String eventsHeading;
    @Column(columnDefinition = "TEXT")
    private String eventsSubtitle;

    // Awards Section
    @Column(columnDefinition = "TEXT")
    private String awardsBadge;
    @Column(columnDefinition = "TEXT")
    private String awardsHeading;
    @Column(columnDefinition = "TEXT")
    private String awardsSubtitle;

    // Media Coverage Section
    @Column(columnDefinition = "TEXT")
    private String coverageBadge;
    @Column(columnDefinition = "TEXT")
    private String coverageHeading;
    @Column(columnDefinition = "TEXT")
    private String coverageSubtitle;

    // Newsletter CTA
    @Column(columnDefinition = "TEXT")
    private String ctaHeading;
    @Column(columnDefinition = "TEXT")
    private String ctaHighlightText;
    @Column(columnDefinition = "TEXT")
    private String ctaDescription;
    @Column(columnDefinition = "TEXT")
    private String ctaButtonText;
    @Column(columnDefinition = "TEXT")
    private String ctaPlaceholder;

    // Relations
    @OneToMany(mappedBy = "mediaEventsPage", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    @Builder.Default
    private List<PressRelease> pressReleases = new ArrayList<>();

    @OneToMany(mappedBy = "mediaEventsPage", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    @Builder.Default
    private List<CompanyEvent> events = new ArrayList<>();

    @OneToMany(mappedBy = "mediaEventsPage", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    @Builder.Default
    private List<Award> awards = new ArrayList<>();

    @OneToMany(mappedBy = "mediaEventsPage", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    @Builder.Default
    private List<MediaCoverage> coverages = new ArrayList<>();

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