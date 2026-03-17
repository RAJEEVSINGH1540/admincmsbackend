package admin.panel.entity.aboutus;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "about_thinking_paragraphs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboutThinkingParagraph {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private Integer sortOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thinking_big_id")
    private AboutThinkingBig thinkingBig;

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