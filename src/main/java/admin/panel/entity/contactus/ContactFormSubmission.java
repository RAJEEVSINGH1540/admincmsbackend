package admin.panel.entity.contactus;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contact_form_submissions")
@Data
public class ContactFormSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 300)
    private String topic;

    @Column(length = 300)
    private String name;

    @Column(length = 300)
    private String email;

    @Column(length = 5000)
    private String message;

    @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ContactFormFieldValue> additionalFields = new ArrayList<>();

    private Boolean isRead;

    private LocalDateTime submittedAt;

    @PrePersist
    protected void onCreate() {
        submittedAt = LocalDateTime.now();
        if (isRead == null) isRead = false;
    }
}