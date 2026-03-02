package admin.panel.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "about_banner")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboutBanner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String image;

    @Column(length = 500)
    private String heading;

    @Column(columnDefinition = "TEXT")
    private String paragraph;

    @Column(length = 500)
    private String heading2;

    @Column(columnDefinition = "TEXT")
    private String paragraph2;
}