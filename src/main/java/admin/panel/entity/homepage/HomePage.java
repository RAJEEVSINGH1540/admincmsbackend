package admin.panel.entity.homepage;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "home_page")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomePage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String heroVideoUrl;

    @Column(columnDefinition = "TEXT")
    private String heroHeading;

    @Column(columnDefinition = "TEXT")
    private String heroParagraph;

    @Column(length = 500)
    private String contentVideoUrl;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}