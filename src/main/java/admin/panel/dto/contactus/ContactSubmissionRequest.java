package admin.panel.dto.contactus;

import lombok.Data;
import java.util.Map;

@Data
public class ContactSubmissionRequest {
    private String topic;
    private String name;
    private String email;
    private String message;
    private Map<String, String> additionalFields;
}