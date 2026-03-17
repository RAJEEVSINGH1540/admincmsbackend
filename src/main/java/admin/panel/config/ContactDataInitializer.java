package admin.panel.config;

import admin.panel.entity.contact.ContactusFormField;
import admin.panel.entity.contact.ContactPage;
import admin.panel.entity.contact.ContactUsTopicOption;
import admin.panel.repository.contact.ContactFormFieldRepository;
import admin.panel.repository.contact.ContactPageRepository;
import admin.panel.repository.contact.ContactTopicOptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(5)
@RequiredArgsConstructor
@Slf4j
public class ContactDataInitializer implements CommandLineRunner {

    private final ContactPageRepository contactPageRepository;
    private final ContactFormFieldRepository formFieldRepository;
    private final ContactTopicOptionRepository topicOptionRepository;

    @Override
    public void run(String... args) {
        initContactPage();
        initDefaultFormFields();
        initDefaultTopicOptions();
    }

    private void initContactPage() {
        if (contactPageRepository.count() == 0) {
            ContactPage page = ContactPage.builder()
                    .corporateOfficeTitle("Corporate Office")
                    .corporateOfficeAddress("Way No.196, Jama Al Akbar Street, Building No.344, 1st Floor. (Near Oman LNG), Muscat, Sultanate of Oman")
                    .manufacturingFacilityTitle("Manufacturing Facility")
                    .manufacturingFacilityAddress("Road No 3, Plot No 255-258, 283-286 Nizwa Industrial Estate, Nizwa. Sultanate of Oman.")
                    .formHeading("If you have any inquiries, <br />\nPlease do not hesitate to contact us:")
                    .build();

            contactPageRepository.save(page);
            log.info("✅ Contact page initialized with default data");
        }
    }

    private void initDefaultFormFields() {
        if (formFieldRepository.count() == 0) {
            List<ContactusFormField> defaultFields = List.of(
                    ContactusFormField.builder()
                            .label("Choose Topic")
                            .fieldType("select")
                            .fieldName("topic")
                            .placeholder("Select a topic...")
                            .required(true)
                            .sortOrder(0)
                            .active(true)
                            .isDefault(true)
                            .build(),
                    ContactusFormField.builder()
                            .label("Name")
                            .fieldType("text")
                            .fieldName("name")
                            .placeholder("")
                            .required(true)
                            .sortOrder(1)
                            .active(true)
                            .isDefault(true)
                            .build(),
                    ContactusFormField.builder()
                            .label("Email")
                            .fieldType("email")
                            .fieldName("email")
                            .placeholder("")
                            .required(true)
                            .sortOrder(2)
                            .active(true)
                            .isDefault(true)
                            .build(),
                    ContactusFormField.builder()
                            .label("Message")
                            .fieldType("textarea")
                            .fieldName("message")
                            .placeholder("")
                            .required(true)
                            .sortOrder(3)
                            .active(true)
                            .isDefault(true)
                            .build()
            );

            formFieldRepository.saveAll(defaultFields);
            log.info("✅ Contact form default fields initialized");
        }
    }

    private void initDefaultTopicOptions() {
        if (topicOptionRepository.count() == 0) {
            List<ContactUsTopicOption> defaultTopics = List.of(
                    ContactUsTopicOption.builder()
                            .label("General Inquiry")
                            .value("General Inquiry")
                            .sortOrder(0)
                            .active(true)
                            .build(),
                    ContactUsTopicOption.builder()
                            .label("Engineering & Construction")
                            .value("Engineering & Construction")
                            .sortOrder(1)
                            .active(true)
                            .build(),
                    ContactUsTopicOption.builder()
                            .label("Manufacturing Facility")
                            .value("Manufacturing Facility")
                            .sortOrder(2)
                            .active(true)
                            .build()
            );

            topicOptionRepository.saveAll(defaultTopics);
            log.info("✅ Contact topic options initialized");
        }
    }
}