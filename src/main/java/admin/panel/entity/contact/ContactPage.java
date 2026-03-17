package admin.panel.entity.contact;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "contact_page")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Office Locations Section
    @Column(length = 500)
    private String officeImageUrl;

    @Column(length = 200)
    private String corporateOfficeTitle;

    @Column(columnDefinition = "TEXT")
    private String corporateOfficeAddress;

    @Column(length = 200)
    private String manufacturingFacilityTitle;

    @Column(columnDefinition = "TEXT")
    private String manufacturingFacilityAddress;

    // Contact Form Section
    @Column(columnDefinition = "TEXT")
    private String formHeading;

    @Column(length = 500)
    private String formBackgroundImageUrl;

    // Map Section
    @Column(length = 500)
    private String mapImageUrl;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}