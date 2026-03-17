package admin.panel.entity.contact;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "contact_submissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String topic;

    @Column(length = 200)
    private String name;

    @Column(length = 200)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(columnDefinition = "JSON")
    private String additionalFields; // stores custom field values as JSON

    @Column(nullable = false)
    private Boolean isRead;

    @CreationTimestamp
    private LocalDateTime submittedAt;
}