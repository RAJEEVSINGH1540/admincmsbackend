package admin.panel.entity.vendor;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "vendor_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String organisation;
    private String designation;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phoneNumber;
    private String licenseNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private VendorRole role = VendorRole.VENDOR;

    @Builder.Default
    private Boolean isEmailVerified = false;

    // NEW: Admin verification
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private VendorVerificationStatus verificationStatus = VendorVerificationStatus.PENDING;

    private String rejectionReason;
    private LocalDateTime verifiedAt;
    private Long verifiedBy;

    @Builder.Default
    private Boolean isActive = true;

    private String emailVerificationToken;
    private LocalDateTime emailVerificationTokenExpiry;

    private String passwordResetToken;
    private LocalDateTime passwordResetTokenExpiry;

    private String refreshToken;
    private LocalDateTime lastLoginAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "vendor_additional_fields", joinColumns = @JoinColumn(name = "vendor_user_id"))
    @MapKeyColumn(name = "field_key")
    @Column(name = "field_value", columnDefinition = "TEXT")
    @Builder.Default
    private Map<String, String> additionalFields = new HashMap<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}