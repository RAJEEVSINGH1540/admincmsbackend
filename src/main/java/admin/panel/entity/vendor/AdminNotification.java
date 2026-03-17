package admin.panel.entity.vendor;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "admin_notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type; // VENDOR_REGISTRATION, VENDOR_VERIFIED, etc.

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String message;

    private Long referenceId; // vendor user id

    @Builder.Default
    private Boolean isRead = false;

    @CreationTimestamp
    private LocalDateTime createdAt;
}