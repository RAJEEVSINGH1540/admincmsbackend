package admin.panel.entity.mediaandevents;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "media_coverages")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MediaCoverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String outlet;
    private String logoUrl;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String excerpt;
    private String articleUrl;
    private String date;
    private Integer sortOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_events_page_id")
    private MediaEventsPage mediaEventsPage;
}