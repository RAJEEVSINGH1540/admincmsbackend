package admin.panel.dto.contactus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactFormSubmissionDTO {

    private Long id;
    private String topic;
    private String name;
    private String email;
    private String message;
    private Map<String, String> additionalFields;
    private Boolean isRead;
    private LocalDateTime submittedAt;
}