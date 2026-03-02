package admin.panel.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "about_content_image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboutContentImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String image;

    @Column(columnDefinition = "TEXT")
    private String paragraph;

    @Column(length = 500)
    private String heading2;

    @Column(columnDefinition = "TEXT")
    private String paragraph2;
}