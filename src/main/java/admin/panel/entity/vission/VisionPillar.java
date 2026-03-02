package admin.panel.entity.vission;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vision_pillars")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VisionPillar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String icon;

    @Column(columnDefinition = "TEXT")
    private String text;

    private Integer sortOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vision_page_id")
    private VisionPage visionPage;
}