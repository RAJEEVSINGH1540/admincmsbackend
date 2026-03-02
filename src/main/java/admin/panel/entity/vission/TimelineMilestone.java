package admin.panel.entity.vission;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "timeline_milestones")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TimelineMilestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String year;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String icon;
    private String side;
    private Integer sortOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vision_page_id")
    private VisionPage visionPage;
}