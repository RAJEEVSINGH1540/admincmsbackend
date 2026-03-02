package admin.panel.entity.contactus;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contact_us_page")
@Data
public class ContactUsPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hero Crawler Section — FIXED: removed @Column(name=...) conflict
    @ElementCollection
    @CollectionTable(
            name = "contact_hero_images",
            joinColumns = @JoinColumn(name = "contact_page_id")
    )
    @Column(name = "hero_image_url")
    @OrderColumn(name = "sort_order")
    private List<String> heroImages = new ArrayList<>();

    @Column(length = 500)
    private String heroHeading1;

    @Column(length = 500)
    private String heroHeading2;

    // Location Section
    @OneToMany(mappedBy = "contactUsPage", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("sortOrder ASC")
    private List<ContactLocation> locations = new ArrayList<>();

    // Content Image Section
    @Column(name = "content_image", length = 2000)
    private String contentImage;

    @Column(name = "welcome_quote1", length = 1000)
    private String welcomeQuote1;

    @Column(name = "welcome_quote2", length = 1000)
    private String welcomeQuote2;

    // Form Section
    @Column(name = "form_background_image", length = 2000)
    private String formBackgroundImage;

    @Column(name = "form_heading", length = 500)
    private String formHeading;

    @OneToMany(mappedBy = "contactUsPage", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("sortOrder ASC")
    private List<ContactFormField> formFields = new ArrayList<>();

    @OneToMany(mappedBy = "contactUsPage", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("sortOrder ASC")
    private List<ContactTopicOption> topicOptions = new ArrayList<>();

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
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