package admin.panel.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "about_bullet_section")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboutBulletSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sectionKey;

    @Column(length = 1000)
    private String image;

    @Column(length = 500)
    private String heading;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "about_bullet_points",
        joinColumns = @JoinColumn(name = "section_id")
    )
    @Column(name = "bullet_point", length = 1000)
    @OrderColumn(name = "bullet_order")
    @Builder.Default
    private List<String> bullets = new ArrayList<>();
}