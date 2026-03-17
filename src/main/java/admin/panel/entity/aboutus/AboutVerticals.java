package admin.panel.entity.aboutus;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "about_verticals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboutVerticals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String title;

    private String image1Url;
    private String image1Alt;

    private String image2Url;
    private String image2Alt;

    private String image3Url;
    private String image3Alt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}