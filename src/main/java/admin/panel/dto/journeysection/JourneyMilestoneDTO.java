package admin.panel.dto.journeysection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JourneyMilestoneDTO {

    private Long id;

    @NotBlank(message = "Era is required")
    private String era;

    @NotBlank(message = "Year is required")
    private String year;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    private String image;

    @NotNull(message = "Display order is required")
    private Integer displayOrder;

    private Boolean active = true;
}