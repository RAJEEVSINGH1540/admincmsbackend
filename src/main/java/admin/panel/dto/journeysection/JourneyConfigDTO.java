package admin.panel.dto.journeysection;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JourneyConfigDTO {

    private Long id;

    @NotBlank(message = "Section heading is required")
    private String sectionHeading;

    @NotBlank(message = "Section subtext is required")
    private String sectionSubtext;

    private String backgroundImage;
}