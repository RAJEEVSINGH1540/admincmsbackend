package admin.panel.entity.contact;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "contact_topic_options")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactUsTopicOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String label;

    @Column(nullable = false, length = 200)
    private String value;

    @Column(nullable = false)
    private Integer sortOrder;

    @Column(nullable = false)
    private Boolean active;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}