package admin.panel.service.contact;

import admin.panel.dto.contact.ContactFormFieldDTO;
import admin.panel.dto.contact.ContactPageDTO;
import admin.panel.dto.contact.ContactSubmissionDTO;
import admin.panel.dto.contact.ContactTopicOptionDTO;
import admin.panel.entity.contact.ContactusFormField;
import admin.panel.entity.contact.ContactPage;
import admin.panel.entity.contact.ContactSubmission;
import admin.panel.entity.contact.ContactUsTopicOption;
import admin.panel.repository.contact.ContactFormFieldRepository;
import admin.panel.repository.contact.ContactPageRepository;
import admin.panel.repository.contact.ContactSubmissionRepository;
import admin.panel.repository.contact.ContactTopicOptionRepository;
import admin.panel.service.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class ContactPageService {

    private final ContactPageRepository contactPageRepository;
    private final ContactFormFieldRepository formFieldRepository;
    private final ContactTopicOptionRepository topicOptionRepository;
    private final ContactSubmissionRepository submissionRepository;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    // ======================== CONTACT PAGE ========================

    public ContactPageDTO getContactPage() {
        ContactPage page = contactPageRepository.findFirstByOrderByIdAsc()
                .orElse(new ContactPage());

        List<ContactFormFieldDTO> fields = formFieldRepository.findAllByOrderBySortOrderAsc()
                .stream().map(this::toFieldDTO).collect(Collectors.toList());

        List<ContactTopicOptionDTO> topics = topicOptionRepository.findAllByOrderBySortOrderAsc()
                .stream().map(this::toTopicDTO).collect(Collectors.toList());

        ContactPageDTO dto = toPageDTO(page);
        dto.setFormFields(fields);
        dto.setTopicOptions(topics);
        return dto;
    }

    public ContactPageDTO getPublicContactPage() {
        ContactPage page = contactPageRepository.findFirstByOrderByIdAsc()
                .orElse(new ContactPage());

        List<ContactFormFieldDTO> fields = formFieldRepository.findByActiveTrueOrderBySortOrderAsc()
                .stream().map(this::toFieldDTO).collect(Collectors.toList());

        List<ContactTopicOptionDTO> topics = topicOptionRepository.findByActiveTrueOrderBySortOrderAsc()
                .stream().map(this::toTopicDTO).collect(Collectors.toList());

        ContactPageDTO dto = toPageDTO(page);
        dto.setFormFields(fields);
        dto.setTopicOptions(topics);
        return dto;
    }

    @Transactional
    public ContactPageDTO updateContactPage(
            String corporateOfficeTitle,
            String corporateOfficeAddress,
            String manufacturingFacilityTitle,
            String manufacturingFacilityAddress,
            String formHeading,
            MultipartFile officeImage,
            MultipartFile formBackgroundImage,
            MultipartFile mapImage
    ) {
        ContactPage page = contactPageRepository.findFirstByOrderByIdAsc()
                .orElse(new ContactPage());

        if (corporateOfficeTitle != null) page.setCorporateOfficeTitle(corporateOfficeTitle);
        if (corporateOfficeAddress != null) page.setCorporateOfficeAddress(corporateOfficeAddress);
        if (manufacturingFacilityTitle != null) page.setManufacturingFacilityTitle(manufacturingFacilityTitle);
        if (manufacturingFacilityAddress != null) page.setManufacturingFacilityAddress(manufacturingFacilityAddress);
        if (formHeading != null) page.setFormHeading(formHeading);

        if (officeImage != null && !officeImage.isEmpty()) {
            String url = saveFile(officeImage, "contact");
            page.setOfficeImageUrl(url);
        }
        if (formBackgroundImage != null && !formBackgroundImage.isEmpty()) {
            String url = saveFile(formBackgroundImage, "contact");
            page.setFormBackgroundImageUrl(url);
        }
        if (mapImage != null && !mapImage.isEmpty()) {
            String url = saveFile(mapImage, "contact");
            page.setMapImageUrl(url);
        }

        contactPageRepository.save(page);
        return getContactPage();
    }

    // ======================== FORM FIELDS ========================

    public ContactFormFieldDTO addFormField(ContactFormFieldDTO dto) {
        if (formFieldRepository.existsByFieldName(dto.getFieldName())) {
            throw new IllegalArgumentException("Field name '" + dto.getFieldName() + "' already exists");
        }

        ContactusFormField field = ContactusFormField.builder()
                .label(dto.getLabel())
                .fieldType(dto.getFieldType())
                .fieldName(dto.getFieldName())
                .placeholder(dto.getPlaceholder())
                .required(dto.getRequired() != null ? dto.getRequired() : false)
                .sortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : 100)
                .active(dto.getActive() != null ? dto.getActive() : true)
                .isDefault(false)
                .build();

        return toFieldDTO(formFieldRepository.save(field));
    }

    public ContactFormFieldDTO updateFormField(Long id, ContactFormFieldDTO dto) {
        ContactusFormField field = formFieldRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Form field not found"));

        if (dto.getLabel() != null) field.setLabel(dto.getLabel());
        if (dto.getFieldType() != null) field.setFieldType(dto.getFieldType());
        if (dto.getPlaceholder() != null) field.setPlaceholder(dto.getPlaceholder());
        if (dto.getRequired() != null) field.setRequired(dto.getRequired());
        if (dto.getSortOrder() != null) field.setSortOrder(dto.getSortOrder());
        if (dto.getActive() != null) field.setActive(dto.getActive());

        // Don't allow changing fieldName of default fields
        if (!field.getIsDefault() && dto.getFieldName() != null) {
            field.setFieldName(dto.getFieldName());
        }

        return toFieldDTO(formFieldRepository.save(field));
    }

    public void deleteFormField(Long id) {
        ContactusFormField field = formFieldRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Form field not found"));

        if (field.getIsDefault()) {
            throw new IllegalArgumentException("Cannot delete default system field: " + field.getLabel());
        }

        formFieldRepository.delete(field);
    }

    @Transactional
    public List<ContactFormFieldDTO> reorderFormFields(List<Long> fieldIds) {
        for (int i = 0; i < fieldIds.size(); i++) {
            ContactusFormField field = formFieldRepository.findById(fieldIds.get(i))
                    .orElseThrow(() -> new RuntimeException("Form field not found"));
            field.setSortOrder(i);
            formFieldRepository.save(field);
        }
        return formFieldRepository.findAllByOrderBySortOrderAsc()
                .stream().map(this::toFieldDTO).collect(Collectors.toList());
    }

    // ======================== TOPIC OPTIONS ========================

    public ContactTopicOptionDTO addTopicOption(ContactTopicOptionDTO dto) {
        ContactUsTopicOption option = ContactUsTopicOption.builder()
                .label(dto.getLabel())
                .value(dto.getValue() != null ? dto.getValue() : dto.getLabel())
                .sortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : 100)
                .active(dto.getActive() != null ? dto.getActive() : true)
                .build();

        return toTopicDTO(topicOptionRepository.save(option));
    }

    public ContactTopicOptionDTO updateTopicOption(Long id, ContactTopicOptionDTO dto) {
        ContactUsTopicOption option = topicOptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic option not found"));

        if (dto.getLabel() != null) option.setLabel(dto.getLabel());
        if (dto.getValue() != null) option.setValue(dto.getValue());
        if (dto.getSortOrder() != null) option.setSortOrder(dto.getSortOrder());
        if (dto.getActive() != null) option.setActive(dto.getActive());

        return toTopicDTO(topicOptionRepository.save(option));
    }

    public void deleteTopicOption(Long id) {
        if (!topicOptionRepository.existsById(id)) {
            throw new RuntimeException("Topic option not found");
        }
        topicOptionRepository.deleteById(id);
    }

    // ======================== SUBMISSIONS ========================

    @Transactional
    public ContactSubmissionDTO submitContactForm(ContactSubmissionDTO dto) {
        // 1. Convert additionalFields Map to JSON string
        String additionalJson = null;
        if (dto.getAdditionalFields() != null && !dto.getAdditionalFields().isEmpty()) {
            try {
                additionalJson = objectMapper.writeValueAsString(dto.getAdditionalFields());
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to process additional fields");
            }
        }

        // 2. Save to database
        ContactSubmission submission = ContactSubmission.builder()
                .topic(dto.getTopic())
                .name(dto.getName())
                .email(dto.getEmail())
                .message(dto.getMessage())
                .additionalFields(additionalJson)
                .isRead(false)
                .build();

        ContactSubmission saved = submissionRepository.save(submission);

        // 3. Send thank-you email to the USER
        try {
            emailService.sendContactThankYouEmail(
                    saved.getEmail(),
                    saved.getName(),
                    saved.getTopic(),
                    saved.getMessage()
            );
        } catch (Exception e) {
            // Log but don't fail — submission is already saved
            log.error("Failed to send thank-you email to {}: {}", saved.getEmail(), e.getMessage());
        }

        // 4. Send notification email to ADMIN
        try {
            emailService.sendAdminContactFormNotification(
                    saved.getName(),
                    saved.getEmail(),
                    saved.getTopic(),
                    saved.getMessage(),
                    dto.getAdditionalFields()
            );
        } catch (Exception e) {
            log.error("Failed to send admin notification email: {}", e.getMessage());
        }

        return toSubmissionDTO(saved);
    }

    public List<ContactSubmissionDTO> getAllSubmissions() {
        return submissionRepository.findAllByOrderBySubmittedAtDesc()
                .stream().map(this::toSubmissionDTO).collect(Collectors.toList());
    }

    public ContactSubmissionDTO markAsRead(Long id) {
        ContactSubmission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Submission not found"));
        submission.setIsRead(true);
        return toSubmissionDTO(submissionRepository.save(submission));
    }

    public void deleteSubmission(Long id) {
        if (!submissionRepository.existsById(id)) {
            throw new RuntimeException("Submission not found");
        }
        submissionRepository.deleteById(id);
    }

    public long getUnreadCount() {
        return submissionRepository.countByIsReadFalse();
    }

    // ======================== FILE HELPER ========================

    private String saveFile(MultipartFile file, String subDir) {
        try {
            String dir = uploadDir + "/" + subDir;
            Files.createDirectories(Paths.get(dir));

            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String filename = UUID.randomUUID() + extension;

            Path filePath = Paths.get(dir, filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return baseUrl + "/uploads/" + subDir + "/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file: " + e.getMessage());
        }
    }

    // ======================== MAPPERS ========================

    private ContactPageDTO toPageDTO(ContactPage page) {
        return ContactPageDTO.builder()
                .id(page.getId())
                .officeImageUrl(page.getOfficeImageUrl())
                .corporateOfficeTitle(page.getCorporateOfficeTitle())
                .corporateOfficeAddress(page.getCorporateOfficeAddress())
                .manufacturingFacilityTitle(page.getManufacturingFacilityTitle())
                .manufacturingFacilityAddress(page.getManufacturingFacilityAddress())
                .formHeading(page.getFormHeading())
                .formBackgroundImageUrl(page.getFormBackgroundImageUrl())
                .mapImageUrl(page.getMapImageUrl())
                .createdAt(page.getCreatedAt())
                .updatedAt(page.getUpdatedAt())
                .build();
    }

    private ContactFormFieldDTO toFieldDTO(ContactusFormField field) {
        return ContactFormFieldDTO.builder()
                .id(field.getId())
                .label(field.getLabel())
                .fieldType(field.getFieldType())
                .fieldName(field.getFieldName())
                .placeholder(field.getPlaceholder())
                .required(field.getRequired())
                .sortOrder(field.getSortOrder())
                .active(field.getActive())
                .isDefault(field.getIsDefault())
                .build();
    }

    private ContactTopicOptionDTO toTopicDTO(ContactUsTopicOption option) {
        return ContactTopicOptionDTO.builder()
                .id(option.getId())
                .label(option.getLabel())
                .value(option.getValue())
                .sortOrder(option.getSortOrder())
                .active(option.getActive())
                .build();
    }

    private ContactSubmissionDTO toSubmissionDTO(ContactSubmission submission) {
        Map<String, String> additionalMap = null;
        if (submission.getAdditionalFields() != null) {
            try {
                additionalMap = objectMapper.readValue(
                        submission.getAdditionalFields(),
                        new TypeReference<Map<String, String>>() {}
                );
            } catch (JsonProcessingException e) {
                additionalMap = new HashMap<>();
            }
        }

        return ContactSubmissionDTO.builder()
                .id(submission.getId())
                .topic(submission.getTopic())
                .name(submission.getName())
                .email(submission.getEmail())
                .message(submission.getMessage())
                .additionalFields(additionalMap)
                .isRead(submission.getIsRead())
                .submittedAt(submission.getSubmittedAt())
                .build();
    }
}