package admin.panel.entity.vendorsuplier;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vendor_supplier_forms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorSupplierForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String formHeading;

    @Column(columnDefinition = "TEXT")
    private String formDescription;

    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("sortOrder ASC")
    @Builder.Default
    private List<VendorFormField> fields = new ArrayList<>();

    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Helper method to manage bidirectional relationship
    public void addField(VendorFormField field) {
        fields.add(field);
        field.setForm(this);
    }

    public void removeField(VendorFormField field) {
        fields.remove(field);
        field.setForm(null);
    }

    public void clearFields() {
        for (VendorFormField field : new ArrayList<>(fields)) {
            removeField(field);
        }
    }
}