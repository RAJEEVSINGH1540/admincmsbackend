package admin.panel.entity.services;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "services")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    @Column(nullable = false)
    private String title;

    @Column(length = 500)
    private String imageUrl;

    @ElementCollection
    @CollectionTable(name = "service_paragraphs", joinColumns = @JoinColumn(name = "service_id"))
    @Column(name = "paragraph", columnDefinition = "TEXT")
    @OrderColumn(name = "paragraph_order")
    @Builder.Default
    private List<String> paragraphs = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}