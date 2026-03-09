package admin.panel.service.journeysection;

import admin.panel.dto.journeysection.JourneyConfigDTO;
import admin.panel.dto.journeysection.JourneyMilestoneDTO;
import admin.panel.entity.journeysection.JourneyConfig;
import admin.panel.entity.journeysection.JourneyMilestone;
import admin.panel.repository.journeysection.JourneyConfigRepository;
import admin.panel.repository.journeysection.JourneyMilestoneRepository;
import admin.panel.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JourneyService {

    private final JourneyMilestoneRepository milestoneRepository;
    private final JourneyConfigRepository configRepository;
    private final FileStorageService fileStorageService;

    // ==================== CONFIG ====================

    public JourneyConfigDTO getConfig() {
        JourneyConfig config = configRepository.findAll().stream()
                .findFirst()
                .orElseGet(() -> configRepository.save(JourneyConfig.builder()
                        .sectionHeading("PIVOTAL MILESTONES IN OUR EVOLUTION")
                        .sectionSubtext("A heritage spanning decades of relentless pursuit for engineering mastery.")
                        .backgroundImage("https://images.unsplash.com/photo-1504307651254-35680f356dfd?w=1920&h=1080&fit=crop")
                        .build()));
        return mapConfigToDTO(config);
    }

    @Transactional
    public JourneyConfigDTO updateConfig(JourneyConfigDTO dto, MultipartFile bgImageFile) {
        JourneyConfig config = configRepository.findAll().stream()
                .findFirst()
                .orElse(new JourneyConfig());

        config.setSectionHeading(dto.getSectionHeading());
        config.setSectionSubtext(dto.getSectionSubtext());

        if (bgImageFile != null && !bgImageFile.isEmpty()) {
            if (config.getBackgroundImage() != null && config.getBackgroundImage().startsWith("/uploads/")) {
                fileStorageService.deleteFile(config.getBackgroundImage());
            }
            String path = fileStorageService.storeImage(bgImageFile, "journey");
            config.setBackgroundImage(path);
        } else if (dto.getBackgroundImage() != null && !dto.getBackgroundImage().isBlank()) {
            if (config.getBackgroundImage() != null && config.getBackgroundImage().startsWith("/uploads/")
                    && !dto.getBackgroundImage().equals(config.getBackgroundImage())) {
                fileStorageService.deleteFile(config.getBackgroundImage());
            }
            config.setBackgroundImage(dto.getBackgroundImage());
        }

        JourneyConfig saved = configRepository.save(config);
        log.info("Journey config updated: {}", saved.getId());
        return mapConfigToDTO(saved);
    }

    // ==================== MILESTONES ====================

    public List<JourneyMilestoneDTO> getAllMilestones() {
        return milestoneRepository.findAllByOrderByDisplayOrderAsc().stream()
                .map(this::mapMilestoneToDTO)
                .collect(Collectors.toList());
    }

    public List<JourneyMilestoneDTO> getActiveMilestones() {
        return milestoneRepository.findByActiveTrueOrderByDisplayOrderAsc().stream()
                .map(this::mapMilestoneToDTO)
                .collect(Collectors.toList());
    }

    public JourneyMilestoneDTO getMilestoneById(Long id) {
        return milestoneRepository.findById(id)
                .map(this::mapMilestoneToDTO)
                .orElseThrow(() -> new RuntimeException("Milestone not found with id: " + id));
    }

    @Transactional
    public JourneyMilestoneDTO createMilestone(JourneyMilestoneDTO dto, MultipartFile imageFile) {
        JourneyMilestone milestone = JourneyMilestone.builder()
                .era(dto.getEra())
                .year(dto.getYear())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .displayOrder(dto.getDisplayOrder())
                .active(dto.getActive() != null ? dto.getActive() : true)
                .build();

        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = fileStorageService.storeImage(imageFile, "journey");
            milestone.setImage(imagePath);
        } else if (dto.getImage() != null && !dto.getImage().isBlank()) {
            milestone.setImage(dto.getImage());
        } else {
            milestone.setImage("https://images.unsplash.com/photo-1504307651254-35680f356dfd?w=1200&h=600&fit=crop");
        }

        JourneyMilestone saved = milestoneRepository.save(milestone);
        log.info("Journey milestone created: {} (id={})", saved.getTitle(), saved.getId());
        return mapMilestoneToDTO(saved);
    }

    @Transactional
    public JourneyMilestoneDTO updateMilestone(Long id, JourneyMilestoneDTO dto, MultipartFile imageFile) {
        JourneyMilestone milestone = milestoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Milestone not found with id: " + id));

        milestone.setEra(dto.getEra());
        milestone.setYear(dto.getYear());
        milestone.setTitle(dto.getTitle());
        milestone.setDescription(dto.getDescription());
        milestone.setDisplayOrder(dto.getDisplayOrder());

        if (dto.getActive() != null) {
            milestone.setActive(dto.getActive());
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            if (milestone.getImage() != null && milestone.getImage().startsWith("/uploads/")) {
                fileStorageService.deleteFile(milestone.getImage());
            }
            String imagePath = fileStorageService.storeImage(imageFile, "journey");
            milestone.setImage(imagePath);
        } else if (dto.getImage() != null && !dto.getImage().isBlank()) {
            if (milestone.getImage() != null && milestone.getImage().startsWith("/uploads/")
                    && !dto.getImage().equals(milestone.getImage())) {
                fileStorageService.deleteFile(milestone.getImage());
            }
            milestone.setImage(dto.getImage());
        }

        JourneyMilestone saved = milestoneRepository.save(milestone);
        log.info("Journey milestone updated: {} (id={})", saved.getTitle(), saved.getId());
        return mapMilestoneToDTO(saved);
    }

    @Transactional
    public void deleteMilestone(Long id) {
        JourneyMilestone milestone = milestoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Milestone not found with id: " + id));

        if (milestone.getImage() != null && milestone.getImage().startsWith("/uploads/")) {
            fileStorageService.deleteFile(milestone.getImage());
        }

        milestoneRepository.delete(milestone);
        log.info("Journey milestone deleted: id={}", id);
    }

    @Transactional
    public JourneyMilestoneDTO toggleMilestoneActive(Long id) {
        JourneyMilestone milestone = milestoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Milestone not found with id: " + id));

        milestone.setActive(!milestone.getActive());
        JourneyMilestone saved = milestoneRepository.save(milestone);
        log.info("Journey milestone toggled: id={}, active={}", saved.getId(), saved.getActive());
        return mapMilestoneToDTO(saved);
    }

    @Transactional
    public List<JourneyMilestoneDTO> reorderMilestones(List<Long> orderedIds) {
        for (int i = 0; i < orderedIds.size(); i++) {
            Long milestoneId = orderedIds.get(i);
            JourneyMilestone milestone = milestoneRepository.findById(milestoneId)
                    .orElseThrow(() -> new RuntimeException("Milestone not found: " + milestoneId));
            milestone.setDisplayOrder(i + 1);
            milestoneRepository.save(milestone);
        }
        log.info("Journey milestones reordered: {} items", orderedIds.size());
        return getAllMilestones();
    }

    // ==================== MAPPERS ====================

    private JourneyMilestoneDTO mapMilestoneToDTO(JourneyMilestone entity) {
        return JourneyMilestoneDTO.builder()
                .id(entity.getId())
                .era(entity.getEra())
                .year(entity.getYear())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .image(entity.getImage())
                .displayOrder(entity.getDisplayOrder())
                .active(entity.getActive())
                .build();
    }

    private JourneyConfigDTO mapConfigToDTO(JourneyConfig entity) {
        return JourneyConfigDTO.builder()
                .id(entity.getId())
                .sectionHeading(entity.getSectionHeading())
                .sectionSubtext(entity.getSectionSubtext())
                .backgroundImage(entity.getBackgroundImage())
                .build();
    }
}