package admin.panel.entity.media;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "media_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==================== BANNER SECTION ====================
    @Column(name = "banner_image_url")
    private String bannerImageUrl;

    @Column(name = "card_image_url")
    private String cardImageUrl;

    @Column(name = "card_date")
    private String cardDate;

    @Column(name = "card_description", columnDefinition = "TEXT")
    private String cardDescription;

    // ==================== ARTICLE SECTION ====================
    @Column(name = "article_title", columnDefinition = "TEXT")
    private String articleTitle;

    @Column(name = "article_paragraphs", columnDefinition = "LONGTEXT")
    private String articleParagraphs; // JSON array of strings

    @Column(name = "content_image_url")
    private String contentImageUrl;

    @Column(name = "image_below_paragraphs", columnDefinition = "LONGTEXT")
    private String imageBelowParagraphs; // JSON array of strings

    @Column(name = "article_date")
    private String articleDate;

    // ==================== META ====================
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "display_order")
    @Builder.Default
    private Integer displayOrder = 0;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}