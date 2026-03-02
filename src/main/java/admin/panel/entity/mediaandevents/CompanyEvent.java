package admin.panel.entity.mediaandevents;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "company_events")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CompanyEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String date;
    private String time;
    private String location;
    private String imageUrl;
    private String status; // upcoming, ongoing, completed
    private String statusColor;
    private String registerUrl;
    private Integer sortOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_events_page_id")
    private MediaEventsPage mediaEventsPage;
}