package admin.panel.dto.contactus;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ContactSubmissionResponse {
    private Long id;
    private String topic;
    private String name;
    private String email;
    private String message;
    private Map<String, String> additionalFields;
    private Boolean isRead;
    private LocalDateTime submittedAt;
}