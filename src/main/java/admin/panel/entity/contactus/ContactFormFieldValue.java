package admin.panel.entity.contactus;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "contact_form_field_values")
@Data
public class ContactFormFieldValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String fieldName;

    @Column(length = 200)
    private String fieldLabel;

    @Column(length = 5000)
    private String fieldValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submission_id")
    private ContactFormSubmission submission;
}