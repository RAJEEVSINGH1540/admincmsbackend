package admin.panel.entity.contactus;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "contact_locations")
@Data
public class ContactLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 5000)
    private String mapIframe;

    @Column(length = 200)
    private String locationBadge;

    @Column(length = 300)
    private String heading;

    @Column(length = 100)
    private String icon;

    @Column(length = 1000)
    private String address;

    @Column(length = 200)
    private String country;

    @Column(length = 1000)
    private String directionsUrl;

    private Integer sortOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_page_id")
    private ContactUsPage contactUsPage;
}