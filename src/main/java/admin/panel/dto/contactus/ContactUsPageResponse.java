package admin.panel.dto.contactus;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ContactUsPageResponse {

    private Long id;

    // Hero Section
    private List<String> heroImages;
    private String heroHeading1;
    private String heroHeading2;

    // Locations
    private List<LocationResponse> locations;

    // Content Image Section
    private String contentImage;
    private String welcomeQuote1;
    private String welcomeQuote2;

    // Form Section
    private String formBackgroundImage;
    private String formHeading;
    private List<FormFieldResponse> formFields;
    private List<TopicOptionResponse> topicOptions;

    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    public static class LocationResponse {
        private Long id;
        private String mapIframe;
        private String locationBadge;
        private String heading;
        private String icon;
        private String address;
        private String country;
        private String directionsUrl;
        private Integer sortOrder;
    }

    @Data
    public static class FormFieldResponse {
        private Long id;
        private String fieldName;
        private String fieldLabel;
        private String fieldType;
        private String placeholder;
        private Boolean isRequired;
        private Integer sortOrder;
        private Boolean isActive;
    }

    @Data
    public static class TopicOptionResponse {
        private Long id;
        private String optionValue;
        private String optionLabel;
        private Integer sortOrder;
        private Boolean isActive;
    }
}