package admin.panel.entity.vission;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commitments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Commitment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String iconName;
    private Integer sortOrder;

    @ElementCollection
    @CollectionTable(name = "commitment_items", joinColumns = @JoinColumn(name = "commitment_id"))
    @Column(name = "item_text", columnDefinition = "TEXT")
    @OrderColumn(name = "item_order")
    @Builder.Default
    private List<String> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vision_page_id")
    private VisionPage visionPage;
}