package admin.panel.dto.services;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceResponse {
    private Long serviceId;
    private String title;
    private String imageUrl;
    private List<String> paragraphs;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}