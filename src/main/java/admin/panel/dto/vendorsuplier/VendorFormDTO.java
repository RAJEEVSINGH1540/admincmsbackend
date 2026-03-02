package admin.panel.dto.vendorsuplier;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorFormDTO {

    private Long id;
    private String formHeading;
    private String formDescription;
    private List<VendorFormFieldDTO> fields;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}