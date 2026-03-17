package admin.panel.entity.vendor;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "vendor_form_fields")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorFormField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String fieldKey; // e.g., "gst_number"

    @Column(nullable = false)
    private String fieldLabel; // e.g., "GST Number"

    @Column(nullable = false)
    @Builder.Default
    private String fieldType = "text"; // text, email, tel, number, textarea, select

    private String placeholder;

    @Column(columnDefinition = "TEXT")
    private String options; // JSON array for select type: ["Option1","Option2"]

    @Builder.Default
    private Boolean isRequired = false;

    @Builder.Default
    private Boolean isActive = true;

    @Builder.Default
    private Integer displayOrder = 0;

    private String validationPattern; // regex pattern
    private String validationMessage; // custom error message
    private Integer minLength;
    private Integer maxLength;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}