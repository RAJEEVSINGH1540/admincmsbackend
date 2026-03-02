package admin.panel.entity.mediaandevents;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "awards")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Award {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String year;
    private String organization;
    private String imageUrl;
    private String accentColor;
    private Integer sortOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_events_page_id")
    private MediaEventsPage mediaEventsPage;
}