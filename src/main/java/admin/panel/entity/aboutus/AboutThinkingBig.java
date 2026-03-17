package admin.panel.entity.aboutus;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "about_thinking_big")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboutThinkingBig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(mappedBy = "thinkingBig", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("sortOrder ASC")
    @Builder.Default
    private List<AboutThinkingParagraph> paragraphs = new ArrayList<>();

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

    // Helper methods
    public void addParagraph(AboutThinkingParagraph paragraph) {
        paragraphs.add(paragraph);
        paragraph.setThinkingBig(this);
    }

    public void removeParagraph(AboutThinkingParagraph paragraph) {
        paragraphs.remove(paragraph);
        paragraph.setThinkingBig(null);
    }
}