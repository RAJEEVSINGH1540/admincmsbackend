package admin.panel.entity.contactus;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "contact_form_fields")
@Data
public class ContactFormField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String fieldName;

    @Column(length = 200)
    private String fieldLabel;

    @Column(length = 50)
    private String fieldType; // text, email, textarea, dropdown, tel, number

    @Column(length = 300)
    private String placeholder;

    private Boolean isRequired;

    private Integer sortOrder;

    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_page_id")
    private ContactUsPage contactUsPage;
}