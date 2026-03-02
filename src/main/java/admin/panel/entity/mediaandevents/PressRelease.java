package admin.panel.entity.mediaandevents;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "press_releases")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PressRelease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(columnDefinition = "TEXT")
    private String summary;
    private String date;
    private String source;
    private String sourceUrl;
    private String imageUrl;
    private String category;
    private String tagColor;
    private Integer sortOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_events_page_id")
    private MediaEventsPage mediaEventsPage;
}