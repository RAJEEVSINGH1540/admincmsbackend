package admin.panel.dto.vendorsuplier;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorFormRequest {

    private String formHeading;
    private String formDescription;
    private List<VendorFormFieldDTO> fields;
    private Boolean isActive;
}