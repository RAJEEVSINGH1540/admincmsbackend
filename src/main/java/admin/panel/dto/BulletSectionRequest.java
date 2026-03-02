package admin.panel.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BulletSectionRequest {
    private String image;
    private String heading;
    private List<String> bullets;
}