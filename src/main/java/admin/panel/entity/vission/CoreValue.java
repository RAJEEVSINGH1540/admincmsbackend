package admin.panel.entity.vission;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "core_values")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CoreValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String iconName;
    private String accentColorFrom;
    private String accentColorTo;
    private String hoverBgColor;
    private String hoverBorderColor;
    private Integer sortOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vision_page_id")
    private VisionPage visionPage;
}