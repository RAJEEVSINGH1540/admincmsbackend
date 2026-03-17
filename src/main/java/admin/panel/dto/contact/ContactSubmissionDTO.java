package admin.panel.dto.contact;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactSubmissionDTO {

    private Long id;
    private String topic;
    private String name;
    private String email;
    private String message;
    private Map<String, String> additionalFields;
    private Boolean isRead;
    private LocalDateTime submittedAt;
}