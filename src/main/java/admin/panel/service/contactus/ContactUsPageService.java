package admin.panel.service.contactus;

import admin.panel.dto.contactus.*;
import admin.panel.entity.contactus.*;
import admin.panel.repository.contactus.ContactUsPageRepository;
import admin.panel.repository.contactus.ContactFormSubmissionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContactUsPageService {

    private final ContactUsPageRepository pageRepository;
    private final ContactFormSubmissionRepository submissionRepository;

    public ContactUsPageService(ContactUsPageRepository pageRepository,
                                ContactFormSubmissionRepository submissionRepository) {
        this.pageRepository = pageRepository;
        this.submissionRepository = submissionRepository;
    }

    // ==================== Page CRUD ====================

    @Transactional(readOnly = true)
    public ContactUsPageResponse getPageById(Long id) {
        ContactUsPage page = pageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact page not found with id: " + id));
        return toResponse(page);
    }

    @Transactional(readOnly = true)
    public ContactUsPageResponse getActivePage() {
        ContactUsPage page = pageRepository.findActiveContactPage()
                .orElseThrow(() -> new RuntimeException("No active contact page found"));
        return toResponse(page);
    }

    @Transactional(readOnly = true)
    public List<ContactUsPageResponse> getAllPages() {
        return pageRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ContactUsPageResponse createPage(ContactUsPageRequest request) {
        ContactUsPage page = new ContactUsPage();
        mapRequestToEntity(request, page);
        ContactUsPage saved = pageRepository.save(page);
        return toResponse(saved);
    }

    @Transactional
    public ContactUsPageResponse updatePage(Long id, ContactUsPageRequest request) {
        ContactUsPage page = pageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact page not found with id: " + id));
        mapRequestToEntity(request, page);
        ContactUsPage saved = pageRepository.save(page);
        return toResponse(saved);
    }

    @Transactional
    public void deletePage(Long id) {
        if (!pageRepository.existsById(id)) {
            throw new RuntimeException("Contact page not found with id: " + id);
        }
        pageRepository.deleteById(id);
    }

    // ==================== Hero Image Operations ====================

    @Transactional
    public ContactUsPageResponse addHeroImage(Long pageId, String imageUrl) {
        ContactUsPage page = pageRepository.findById(pageId)
                .orElseThrow(() -> new RuntimeException("Contact page not found with id: " + pageId));

        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            page.getHeroImages().add(imageUrl.trim());
        }

        ContactUsPage saved = pageRepository.save(page);
        return toResponse(saved);
    }

    @Transactional
    public ContactUsPageResponse removeHeroImage(Long pageId, int index) {
        ContactUsPage page = pageRepository.findById(pageId)
                .orElseThrow(() -> new RuntimeException("Contact page not found with id: " + pageId));

        if (index >= 0 && index < page.getHeroImages().size()) {
            page.getHeroImages().remove(index);
        }

        ContactUsPage saved = pageRepository.save(page);
        return toResponse(saved);
    }

    // ==================== Location Operations ====================

    @Transactional
    public ContactUsPageResponse addLocation(Long pageId, ContactUsPageRequest.LocationRequest locationReq) {
        ContactUsPage page = pageRepository.findById(pageId)
                .orElseThrow(() -> new RuntimeException("Contact page not found with id: " + pageId));

        ContactLocation location = new ContactLocation();
        mapLocationRequest(locationReq, location);
        location.setSortOrder(page.getLocations().size());
        location.setContactUsPage(page);
        page.getLocations().add(location);

        ContactUsPage saved = pageRepository.save(page);
        return toResponse(saved);
    }

    @Transactional
    public ContactUsPageResponse removeLocation(Long pageId, Long locationId) {
        ContactUsPage page = pageRepository.findById(pageId)
                .orElseThrow(() -> new RuntimeException("Contact page not found with id: " + pageId));

        page.getLocations().removeIf(loc -> loc.getId().equals(locationId));

        ContactUsPage saved = pageRepository.save(page);
        return toResponse(saved);
    }

    // ==================== Topic Option Operations ====================

    @Transactional
    public ContactUsPageResponse addTopicOption(Long pageId, ContactUsPageRequest.TopicOptionRequest topicReq) {
        ContactUsPage page = pageRepository.findById(pageId)
                .orElseThrow(() -> new RuntimeException("Contact page not found with id: " + pageId));

        ContactTopicOption option = new ContactTopicOption();
        option.setOptionValue(topicReq.getOptionValue());
        option.setOptionLabel(topicReq.getOptionLabel());
        option.setSortOrder(topicReq.getSortOrder() != null ? topicReq.getSortOrder() : page.getTopicOptions().size());
        option.setIsActive(topicReq.getIsActive() != null ? topicReq.getIsActive() : true);
        option.setContactUsPage(page);
        page.getTopicOptions().add(option);

        ContactUsPage saved = pageRepository.save(page);
        return toResponse(saved);
    }

    @Transactional
    public ContactUsPageResponse removeTopicOption(Long pageId, Long optionId) {
        ContactUsPage page = pageRepository.findById(pageId)
                .orElseThrow(() -> new RuntimeException("Contact page not found with id: " + pageId));

        page.getTopicOptions().removeIf(opt -> opt.getId().equals(optionId));

        ContactUsPage saved = pageRepository.save(page);
        return toResponse(saved);
    }

    // ==================== Form Field Operations ====================

    @Transactional
    public ContactUsPageResponse addFormField(Long pageId, ContactUsPageRequest.FormFieldRequest fieldReq) {
        ContactUsPage page = pageRepository.findById(pageId)
                .orElseThrow(() -> new RuntimeException("Contact page not found with id: " + pageId));

        ContactFormField field = new ContactFormField();
        field.setFieldName(fieldReq.getFieldName());
        field.setFieldLabel(fieldReq.getFieldLabel());
        field.setFieldType(fieldReq.getFieldType());
        field.setPlaceholder(fieldReq.getPlaceholder());
        field.setIsRequired(fieldReq.getIsRequired() != null ? fieldReq.getIsRequired() : true);
        field.setSortOrder(fieldReq.getSortOrder() != null ? fieldReq.getSortOrder() : page.getFormFields().size());
        field.setIsActive(fieldReq.getIsActive() != null ? fieldReq.getIsActive() : true);
        field.setContactUsPage(page);
        page.getFormFields().add(field);

        ContactUsPage saved = pageRepository.save(page);
        return toResponse(saved);
    }

    @Transactional
    public ContactUsPageResponse removeFormField(Long pageId, Long fieldId) {
        ContactUsPage page = pageRepository.findById(pageId)
                .orElseThrow(() -> new RuntimeException("Contact page not found with id: " + pageId));

        page.getFormFields().removeIf(f -> f.getId().equals(fieldId));

        ContactUsPage saved = pageRepository.save(page);
        return toResponse(saved);
    }

    // ==================== Form Submissions ====================

    @Transactional
    public ContactSubmissionResponse submitForm(ContactSubmissionRequest request) {
        ContactFormSubmission submission = new ContactFormSubmission();
        submission.setTopic(request.getTopic());
        submission.setName(request.getName());
        submission.setEmail(request.getEmail());
        submission.setMessage(request.getMessage());

        if (request.getAdditionalFields() != null && !request.getAdditionalFields().isEmpty()) {
            request.getAdditionalFields().forEach((key, value) -> {
                ContactFormFieldValue fieldValue = new ContactFormFieldValue();
                fieldValue.setFieldName(key);
                fieldValue.setFieldLabel(key);
                fieldValue.setFieldValue(value);
                fieldValue.setSubmission(submission);
                submission.getAdditionalFields().add(fieldValue);
            });
        }

        ContactFormSubmission saved = submissionRepository.save(submission);
        return toSubmissionResponse(saved);
    }

    @Transactional(readOnly = true)
    public Page<ContactSubmissionResponse> getAllSubmissions(int page, int size) {
        return submissionRepository.findAllByOrderBySubmittedAtDesc(PageRequest.of(page, size))
                .map(this::toSubmissionResponse);
    }

    @Transactional(readOnly = true)
    public Page<ContactSubmissionResponse> getUnreadSubmissions(int page, int size) {
        return submissionRepository.findByIsReadFalseOrderBySubmittedAtDesc(PageRequest.of(page, size))
                .map(this::toSubmissionResponse);
    }

    @Transactional(readOnly = true)
    public ContactSubmissionResponse getSubmissionById(Long id) {
        ContactFormSubmission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Submission not found with id: " + id));
        return toSubmissionResponse(submission);
    }

    @Transactional(readOnly = true)
    public long getUnreadCount() {
        return submissionRepository.countByIsReadFalse();
    }

    @Transactional
    public void markAsRead(Long id) {
        ContactFormSubmission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Submission not found with id: " + id));
        submission.setIsRead(true);
        submissionRepository.save(submission);
    }

    @Transactional
    public void deleteSubmission(Long id) {
        if (!submissionRepository.existsById(id)) {
            throw new RuntimeException("Submission not found with id: " + id);
        }
        submissionRepository.deleteById(id);
    }

    // ==================== Private Mappers ====================

    private void mapRequestToEntity(ContactUsPageRequest request, ContactUsPage page) {
        // Hero Section
        page.setHeroHeading1(request.getHeroHeading1());
        page.setHeroHeading2(request.getHeroHeading2());

        page.getHeroImages().clear();
        if (request.getHeroImages() != null) {
            page.getHeroImages().addAll(request.getHeroImages());
        }

        // Content Image Section
        page.setContentImage(request.getContentImage());
        page.setWelcomeQuote1(request.getWelcomeQuote1());
        page.setWelcomeQuote2(request.getWelcomeQuote2());

        // Form Section
        page.setFormBackgroundImage(request.getFormBackgroundImage());
        page.setFormHeading(request.getFormHeading());
        page.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);

        // Locations
        page.getLocations().clear();
        if (request.getLocations() != null) {
            for (int i = 0; i < request.getLocations().size(); i++) {
                ContactUsPageRequest.LocationRequest locReq = request.getLocations().get(i);
                ContactLocation location = new ContactLocation();
                mapLocationRequest(locReq, location);
                location.setSortOrder(locReq.getSortOrder() != null ? locReq.getSortOrder() : i);
                location.setContactUsPage(page);
                page.getLocations().add(location);
            }
        }

        // Form Fields
        page.getFormFields().clear();
        if (request.getFormFields() != null) {
            for (int i = 0; i < request.getFormFields().size(); i++) {
                ContactUsPageRequest.FormFieldRequest fieldReq = request.getFormFields().get(i);
                ContactFormField field = new ContactFormField();
                field.setFieldName(fieldReq.getFieldName());
                field.setFieldLabel(fieldReq.getFieldLabel());
                field.setFieldType(fieldReq.getFieldType());
                field.setPlaceholder(fieldReq.getPlaceholder());
                field.setIsRequired(fieldReq.getIsRequired() != null ? fieldReq.getIsRequired() : true);
                field.setSortOrder(fieldReq.getSortOrder() != null ? fieldReq.getSortOrder() : i);
                field.setIsActive(fieldReq.getIsActive() != null ? fieldReq.getIsActive() : true);
                field.setContactUsPage(page);
                page.getFormFields().add(field);
            }
        }

        // Topic Options
        page.getTopicOptions().clear();
        if (request.getTopicOptions() != null) {
            for (int i = 0; i < request.getTopicOptions().size(); i++) {
                ContactUsPageRequest.TopicOptionRequest topicReq = request.getTopicOptions().get(i);
                ContactTopicOption option = new ContactTopicOption();
                option.setOptionValue(topicReq.getOptionValue());
                option.setOptionLabel(topicReq.getOptionLabel());
                option.setSortOrder(topicReq.getSortOrder() != null ? topicReq.getSortOrder() : i);
                option.setIsActive(topicReq.getIsActive() != null ? topicReq.getIsActive() : true);
                option.setContactUsPage(page);
                page.getTopicOptions().add(option);
            }
        }
    }

    private void mapLocationRequest(ContactUsPageRequest.LocationRequest locReq, ContactLocation location) {
        location.setMapIframe(locReq.getMapIframe());
        location.setLocationBadge(locReq.getLocationBadge());
        location.setHeading(locReq.getHeading());
        location.setIcon(locReq.getIcon());
        location.setAddress(locReq.getAddress());
        location.setCountry(locReq.getCountry());
        location.setDirectionsUrl(locReq.getDirectionsUrl());
    }

    private ContactUsPageResponse toResponse(ContactUsPage page) {
        ContactUsPageResponse response = new ContactUsPageResponse();
        response.setId(page.getId());
        response.setHeroHeading1(page.getHeroHeading1());
        response.setHeroHeading2(page.getHeroHeading2());
        response.setHeroImages(new ArrayList<>(page.getHeroImages()));
        response.setContentImage(page.getContentImage());
        response.setWelcomeQuote1(page.getWelcomeQuote1());
        response.setWelcomeQuote2(page.getWelcomeQuote2());
        response.setFormBackgroundImage(page.getFormBackgroundImage());
        response.setFormHeading(page.getFormHeading());
        response.setIsActive(page.getIsActive());
        response.setCreatedAt(page.getCreatedAt());
        response.setUpdatedAt(page.getUpdatedAt());

        // Locations
        if (page.getLocations() != null) {
            List<ContactUsPageResponse.LocationResponse> locationResponses = page.getLocations().stream()
                    .map(loc -> {
                        ContactUsPageResponse.LocationResponse locRes = new ContactUsPageResponse.LocationResponse();
                        locRes.setId(loc.getId());
                        locRes.setMapIframe(loc.getMapIframe());
                        locRes.setLocationBadge(loc.getLocationBadge());
                        locRes.setHeading(loc.getHeading());
                        locRes.setIcon(loc.getIcon());
                        locRes.setAddress(loc.getAddress());
                        locRes.setCountry(loc.getCountry());
                        locRes.setDirectionsUrl(loc.getDirectionsUrl());
                        locRes.setSortOrder(loc.getSortOrder());
                        return locRes;
                    })
                    .collect(Collectors.toList());
            response.setLocations(locationResponses);
        } else {
            response.setLocations(new ArrayList<>());
        }

        // Form Fields
        if (page.getFormFields() != null) {
            List<ContactUsPageResponse.FormFieldResponse> fieldResponses = page.getFormFields().stream()
                    .map(field -> {
                        ContactUsPageResponse.FormFieldResponse fieldRes = new ContactUsPageResponse.FormFieldResponse();
                        fieldRes.setId(field.getId());
                        fieldRes.setFieldName(field.getFieldName());
                        fieldRes.setFieldLabel(field.getFieldLabel());
                        fieldRes.setFieldType(field.getFieldType());
                        fieldRes.setPlaceholder(field.getPlaceholder());
                        fieldRes.setIsRequired(field.getIsRequired());
                        fieldRes.setSortOrder(field.getSortOrder());
                        fieldRes.setIsActive(field.getIsActive());
                        return fieldRes;
                    })
                    .collect(Collectors.toList());
            response.setFormFields(fieldResponses);
        } else {
            response.setFormFields(new ArrayList<>());
        }

        // Topic Options
        if (page.getTopicOptions() != null) {
            List<ContactUsPageResponse.TopicOptionResponse> topicResponses = page.getTopicOptions().stream()
                    .map(topic -> {
                        ContactUsPageResponse.TopicOptionResponse topicRes = new ContactUsPageResponse.TopicOptionResponse();
                        topicRes.setId(topic.getId());
                        topicRes.setOptionValue(topic.getOptionValue());
                        topicRes.setOptionLabel(topic.getOptionLabel());
                        topicRes.setSortOrder(topic.getSortOrder());
                        topicRes.setIsActive(topic.getIsActive());
                        return topicRes;
                    })
                    .collect(Collectors.toList());
            response.setTopicOptions(topicResponses);
        } else {
            response.setTopicOptions(new ArrayList<>());
        }

        return response;
    }

    private ContactSubmissionResponse toSubmissionResponse(ContactFormSubmission submission) {
        ContactSubmissionResponse response = new ContactSubmissionResponse();
        response.setId(submission.getId());
        response.setTopic(submission.getTopic());
        response.setName(submission.getName());
        response.setEmail(submission.getEmail());
        response.setMessage(submission.getMessage());
        response.setIsRead(submission.getIsRead());
        response.setSubmittedAt(submission.getSubmittedAt());

        Map<String, String> additionalFields = new LinkedHashMap<>();
        if (submission.getAdditionalFields() != null) {
            submission.getAdditionalFields().forEach(fv ->
                    additionalFields.put(fv.getFieldName(), fv.getFieldValue()));
        }
        response.setAdditionalFields(additionalFields);

        return response;
    }
}