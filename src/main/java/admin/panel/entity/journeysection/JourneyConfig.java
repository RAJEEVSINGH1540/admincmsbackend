package admin.panel.entity.journeysection;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "journey_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JourneyConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sectionHeading;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String sectionSubtext;

    @Column(nullable = false)
    private String backgroundImage;
}