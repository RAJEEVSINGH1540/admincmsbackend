package admin.panel.entity.vission;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mission_metrics")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MissionMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;
    private String label;
    private String bgColor;
    private String textColor;
    private String borderColor;
    private Integer sortOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vision_page_id")
    private VisionPage visionPage;
}