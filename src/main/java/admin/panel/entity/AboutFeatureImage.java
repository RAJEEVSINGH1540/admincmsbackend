package admin.panel.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "about_feature_image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboutFeatureImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String image;
}