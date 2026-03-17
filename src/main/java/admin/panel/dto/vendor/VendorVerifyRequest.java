package admin.panel.dto.vendor;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorVerifyRequest {
    private String action; // "verify" or "reject"
    private String rejectionReason;
}