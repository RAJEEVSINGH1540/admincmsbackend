package admin.panel.entity.contact;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "contact_form_fields")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactusFormField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String label;

    @Column(nullable = false, length = 50)
    private String fieldType; // text, email, textarea, select

    @Column(nullable = false, length = 100)
    private String fieldName;

    @Column(length = 200)
    private String placeholder;

    @Column(nullable = false)
    private Boolean required;

    @Column(nullable = false)
    private Integer sortOrder;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private Boolean isDefault; // system fields cannot be deleted

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}