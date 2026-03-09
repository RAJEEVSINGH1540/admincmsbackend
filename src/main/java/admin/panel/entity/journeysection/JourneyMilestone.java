package admin.panel.entity.journeysection;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "journey_milestones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JourneyMilestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String era;

    @Column(nullable = false)
    private String year;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private Integer displayOrder;

    @Column(nullable = false)
    private Boolean active = true;
}