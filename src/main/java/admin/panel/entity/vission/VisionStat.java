package admin.panel.entity.vission;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vision_stats")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VisionStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer value;
    private String suffix;
    private String prefix;
    private String label;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String iconName;
    private Integer sortOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vision_page_id")
    private VisionPage visionPage;
}