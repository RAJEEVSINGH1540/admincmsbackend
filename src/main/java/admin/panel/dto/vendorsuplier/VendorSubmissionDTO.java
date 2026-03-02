package admin.panel.dto.vendorsuplier;

import lombok.*;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorSubmissionDTO {

    private Long id;
    private String name;
    private String organisation;
    private String designation;
    private String email;
    private String phoneNumber;
    private String licenseNumber;
    private Map<String, String> additionalFields;
    private Boolean isRead;
    private LocalDateTime submittedAt;
}