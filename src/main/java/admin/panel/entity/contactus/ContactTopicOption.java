package admin.panel.entity.contactus;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "contact_topic_options")
@Data
public class ContactTopicOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String optionValue;

    @Column(length = 200)
    private String optionLabel;

    private Integer sortOrder;

    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_page_id")
    private ContactUsPage contactUsPage;
}