package admin.panel.dto.contact;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactPageDTO {

    private Long id;

    // Office Locations
    private String officeImageUrl;
    private String corporateOfficeTitle;
    private String corporateOfficeAddress;
    private String manufacturingFacilityTitle;
    private String manufacturingFacilityAddress;

    // Form Section
    private String formHeading;
    private String formBackgroundImageUrl;

    // Map
    private String mapImageUrl;

    // Related data
    private List<ContactFormFieldDTO> formFields;
    private List<ContactTopicOptionDTO> topicOptions;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}