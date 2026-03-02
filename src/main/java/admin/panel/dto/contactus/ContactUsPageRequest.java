package admin.panel.dto.contactus;

import lombok.Data;
import java.util.List;

@Data
public class ContactUsPageRequest {

    // Hero Section
    private List<String> heroImages;
    private String heroHeading1;
    private String heroHeading2;

    // Locations
    private List<LocationRequest> locations;

    // Content Image Section
    private String contentImage;
    private String welcomeQuote1;
    private String welcomeQuote2;

    // Form Section
    private String formBackgroundImage;
    private String formHeading;
    private List<FormFieldRequest> formFields;
    private List<TopicOptionRequest> topicOptions;

    private Boolean isActive;

    @Data
    public static class LocationRequest {
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
    public static class FormFieldRequest {
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
    public static class TopicOptionRequest {
        private Long id;
        private String optionValue;
        private String optionLabel;
        private Integer sortOrder;
        private Boolean isActive;
    }
}