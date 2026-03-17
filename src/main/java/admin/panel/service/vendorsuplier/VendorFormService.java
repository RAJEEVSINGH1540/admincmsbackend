//package admin.panel.service.vendorsuplier;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class VendorFormService {
//
//    private final VendorFormRepository formRepository;
//    private final VendorSubmissionRepository submissionRepository;
//    private final VendorUserRepository vendorUserRepository;
//
//
//    // ==================== FORM CONFIG ====================
//
//    @Transactional(readOnly = true)
//    public List<VendorFormDTO> getAllForms() {
//        return formRepository.findAllByOrderByCreatedAtDesc()
//                .stream()
//                .map(this::toFormDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Transactional(readOnly = true)
//    public VendorFormDTO getFormById(Long id) {
//        VendorSupplierForm form = formRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Vendor form not found with id: " + id));
//        return toFormDTO(form);
//    }
//
//    @Transactional
//    public VendorFormDTO createForm(VendorFormRequest request) {
//        VendorSupplierForm form = VendorSupplierForm.builder()
//                .formHeading(request.getFormHeading())
//                .formDescription(request.getFormDescription())
//                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
//                .fields(new ArrayList<>())
//                .build();
//
//        if (request.getFields() != null) {
//            for (int i = 0; i < request.getFields().size(); i++) {
//                VendorFormFieldDTO fieldDTO = request.getFields().get(i);
//                VendorFormField field = toFieldEntity(fieldDTO, i);
//                form.addField(field);
//            }
//        }
//
//        VendorSupplierForm saved = formRepository.save(form);
//        return toFormDTO(saved);
//    }
//
//    @Transactional
//    public VendorFormDTO updateForm(Long id, VendorFormRequest request) {
//        VendorSupplierForm form = formRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Vendor form not found with id: " + id));
//
//        form.setFormHeading(request.getFormHeading());
//        form.setFormDescription(request.getFormDescription());
//        form.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
//
//        // Clear existing fields and re-add
//        form.clearFields();
//
//        if (request.getFields() != null) {
//            for (int i = 0; i < request.getFields().size(); i++) {
//                VendorFormFieldDTO fieldDTO = request.getFields().get(i);
//                VendorFormField field = toFieldEntity(fieldDTO, i);
//                form.addField(field);
//            }
//        }
//
//        VendorSupplierForm saved = formRepository.save(form);
//        return toFormDTO(saved);
//    }
//
//    @Transactional
//    public void deleteForm(Long id) {
//        if (!formRepository.existsById(id)) {
//            throw new RuntimeException("Vendor form not found with id: " + id);
//        }
//        formRepository.deleteById(id);
//    }
//
//    // ==================== SUBMISSIONS ====================
//
//    @Transactional
//    public VendorSubmissionDTO submitForm(VendorSubmissionRequest request) {
//        VendorSubmission submission = VendorSubmission.builder()
//                .name(request.getName())
//                .organisation(request.getOrganisation())
//                .designation(request.getDesignation())
//                .email(request.getEmail())
//                .phoneNumber(request.getPhoneNumber())
//                .licenseNumber(request.getLicenseNumber())
//                .additionalFields(request.getAdditionalFields() != null ? request.getAdditionalFields() : new java.util.HashMap<>())
//                .isRead(false)
//                .build();
//
//        VendorSubmission saved = submissionRepository.save(submission);
//        return toSubmissionDTO(saved);
//    }
//
//    @Transactional(readOnly = true)
//    public Page<VendorSubmissionDTO> getSubmissions(int page, int size) {
//        return submissionRepository.findAllByOrderBySubmittedAtDesc(PageRequest.of(page, size))
//                .map(this::toSubmissionDTO);
//    }
//
//    @Transactional(readOnly = true)
//    public Page<VendorSubmissionDTO> getUnreadSubmissions(int page, int size) {
//        return submissionRepository.findByIsReadFalseOrderBySubmittedAtDesc(PageRequest.of(page, size))
//                .map(this::toSubmissionDTO);
//    }
//
//    @Transactional(readOnly = true)
//    public long getUnreadCount() {
//        return submissionRepository.countByIsReadFalse();
//    }
//
//    @Transactional
//    public void markAsRead(Long id) {
//        VendorSubmission submission = submissionRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Submission not found with id: " + id));
//        submission.setIsRead(true);
//        submissionRepository.save(submission);
//    }
//
//    @Transactional
//    public void deleteSubmission(Long id) {
//        if (!submissionRepository.existsById(id)) {
//            throw new RuntimeException("Submission not found with id: " + id);
//        }
//        submissionRepository.deleteById(id);
//    }
//
//    // ==================== MAPPERS ====================
//
//    private VendorFormDTO toFormDTO(VendorSupplierForm form) {
//        return VendorFormDTO.builder()
//                .id(form.getId())
//                .formHeading(form.getFormHeading())
//                .formDescription(form.getFormDescription())
//                .fields(form.getFields() != null
//                        ? form.getFields().stream().map(this::toFieldDTO).collect(Collectors.toList())
//                        : new ArrayList<>())
//                .isActive(form.getIsActive())
//                .createdAt(form.getCreatedAt())
//                .updatedAt(form.getUpdatedAt())
//                .build();
//    }
//
//    private VendorFormFieldDTO toFieldDTO(VendorFormField field) {
//        return VendorFormFieldDTO.builder()
//                .id(field.getId())
//                .fieldName(field.getFieldName())
//                .fieldLabel(field.getFieldLabel())
//                .fieldType(field.getFieldType())
//                .placeholder(field.getPlaceholder())
//                .isRequired(field.getIsRequired())
//                .sortOrder(field.getSortOrder())
//                .isActive(field.getIsActive())
//                .build();
//    }
//
//    private VendorFormField toFieldEntity(VendorFormFieldDTO dto, int index) {
//        return VendorFormField.builder()
//                .fieldName(dto.getFieldName() != null ? dto.getFieldName() : "field_" + index)
//                .fieldLabel(dto.getFieldLabel() != null ? dto.getFieldLabel() : "Field " + (index + 1))
//                .fieldType(dto.getFieldType() != null ? dto.getFieldType() : "text")
//                .placeholder(dto.getPlaceholder())
//                .isRequired(dto.getIsRequired() != null ? dto.getIsRequired() : false)
//                .sortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : index)
//                .isActive(dto.getIsActive() != null ? dto.getIsActive() : true)
//                .build();
//    }
//
//    private VendorSubmissionDTO toSubmissionDTO(VendorSubmission submission) {
//        return VendorSubmissionDTO.builder()
//                .id(submission.getId())
//                .name(submission.getName())
//                .organisation(submission.getOrganisation())
//                .designation(submission.getDesignation())
//                .email(submission.getEmail())
//                .phoneNumber(submission.getPhoneNumber())
//                .licenseNumber(submission.getLicenseNumber())
//                .additionalFields(submission.getAdditionalFields())
//                .isRead(submission.getIsRead())
//                .submittedAt(submission.getSubmittedAt())
//                .build();
//    }
//
//// ==================== VENDOR USERS (for CMS) ====================
//
//    @Transactional(readOnly = true)
//    public Page<VendorUserListDTO> getAllVendorUsers(int page, int size) {
//        return vendorUserRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size))
//                .map(this::toUserListDTO);
//    }
//
//    @Transactional(readOnly = true)
//    public Page<VendorUserListDTO> getUnverifiedVendorUsers(int page, int size) {
//        return vendorUserRepository.findByIsEmailVerifiedFalseOrderByCreatedAtDesc(PageRequest.of(page, size))
//                .map(this::toUserListDTO);
//    }
//
//    @Transactional(readOnly = true)
//    public long getUnverifiedCount() {
//        return vendorUserRepository.countByIsEmailVerifiedFalse();
//    }
//
//    @Transactional(readOnly = true)
//    public VendorUserListDTO getVendorUserById(Long id) {
//        VendorUser user = vendorUserRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Vendor user not found with id: " + id));
//        return toUserListDTO(user);
//    }
//
//    @Transactional
//    public void deleteVendorUser(Long id) {
//        if (!vendorUserRepository.existsById(id)) {
//            throw new RuntimeException("Vendor user not found with id: " + id);
//        }
//        vendorUserRepository.deleteById(id);
//    }
//
//    @Transactional
//    public void toggleVendorUserActive(Long id) {
//        VendorUser user = vendorUserRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Vendor user not found with id: " + id));
//        user.setIsActive(!user.getIsActive());
//        vendorUserRepository.save(user);
//    }
//
//    // Add this mapper method
//    private VendorUserListDTO toUserListDTO(VendorUser user) {
//        return VendorUserListDTO.builder()
//                .id(user.getId())
//                .name(user.getName())
//                .organisation(user.getOrganisation())
//                .designation(user.getDesignation())
//                .email(user.getEmail())
//                .phoneNumber(user.getPhoneNumber())
//                .licenseNumber(user.getLicenseNumber())
//                .role(user.getRole().name())
//                .isEmailVerified(user.getIsEmailVerified())
//                .isActive(user.getIsActive())
//                .additionalFields(user.getAdditionalFields())
//                .createdAt(user.getCreatedAt())
//                .lastLoginAt(user.getLastLoginAt())
//                .build();
//    }
//}