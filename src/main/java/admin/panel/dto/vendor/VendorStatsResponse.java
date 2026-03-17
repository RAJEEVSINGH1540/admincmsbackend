package admin.panel.dto.vendor;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorStatsResponse {
    private long totalVendors;
    private long pendingVerification;
    private long verified;
    private long rejected;
    private long unreadNotifications;
}