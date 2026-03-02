package admin.panel.dto.vendorsuplier;

import lombok.*;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorSubmissionRequest {

    private String name;
    private String organisation;
    private String designation;
    private String email;
    private String phoneNumber;
    private String licenseNumber;
    private Map<String, String> additionalFields;
}