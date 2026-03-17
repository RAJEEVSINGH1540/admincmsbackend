package admin.panel.dto.vendor;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminNotificationResponse {
    private Long id;
    private String type;
    private String title;
    private String message;
    private Long referenceId;
    private Boolean isRead;
    private LocalDateTime createdAt;
}