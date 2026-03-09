package admin.panel.controller.journeysection;

import admin.panel.dto.ApiResponse;
import admin.panel.dto.journeysection.JourneyConfigDTO;
import admin.panel.dto.journeysection.JourneyMilestoneDTO;
import admin.panel.service.journeysection.JourneyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/journey")
@RequiredArgsConstructor
public class JourneyController {

    private final JourneyService journeyService;

    // ==================== CONFIG ====================

    @GetMapping("/config")
    public ResponseEntity<ApiResponse<JourneyConfigDTO>> getConfig() {
        return ResponseEntity.ok(ApiResponse.success(journeyService.getConfig()));
    }

    @PutMapping(value = "/config", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<JourneyConfigDTO>> updateConfig(
            @RequestParam("sectionHeading") String sectionHeading,
            @RequestParam("sectionSubtext") String sectionSubtext,
            @RequestParam(value = "backgroundImage", required = false) String backgroundImage,
            @RequestParam(value = "bgImageFile", required = false) MultipartFile bgImageFile) {

        JourneyConfigDTO dto = JourneyConfigDTO.builder()
                .sectionHeading(sectionHeading)
                .sectionSubtext(sectionSubtext)
                .backgroundImage(backgroundImage)
                .build();

        return ResponseEntity.ok(ApiResponse.success(journeyService.updateConfig(dto, bgImageFile)));
    }

    // ==================== MILESTONES ====================

    @GetMapping("/milestones")
    public ResponseEntity<ApiResponse<List<JourneyMilestoneDTO>>> getAllMilestones() {
        return ResponseEntity.ok(ApiResponse.success(journeyService.getAllMilestones()));
    }

    @GetMapping("/milestones/active")
    public ResponseEntity<ApiResponse<List<JourneyMilestoneDTO>>> getActiveMilestones() {
        return ResponseEntity.ok(ApiResponse.success(journeyService.getActiveMilestones()));
    }

    @GetMapping("/milestones/{id}")
    public ResponseEntity<ApiResponse<JourneyMilestoneDTO>> getMilestoneById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(journeyService.getMilestoneById(id)));
    }

    @PostMapping(value = "/milestones", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<JourneyMilestoneDTO>> createMilestone(
            @RequestParam("era") String era,
            @RequestParam("year") String year,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("displayOrder") Integer displayOrder,
            @RequestParam(value = "active", defaultValue = "true") Boolean active,
            @RequestParam(value = "image", required = false) String image,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        JourneyMilestoneDTO dto = JourneyMilestoneDTO.builder()
                .era(era).year(year).title(title)
                .description(description).displayOrder(displayOrder)
                .active(active).image(image)
                .build();

        return ResponseEntity.ok(ApiResponse.success(journeyService.createMilestone(dto, imageFile)));
    }

    @PutMapping(value = "/milestones/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<JourneyMilestoneDTO>> updateMilestone(
            @PathVariable Long id,
            @RequestParam("era") String era,
            @RequestParam("year") String year,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("displayOrder") Integer displayOrder,
            @RequestParam(value = "active", defaultValue = "true") Boolean active,
            @RequestParam(value = "image", required = false) String image,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        JourneyMilestoneDTO dto = JourneyMilestoneDTO.builder()
                .era(era).year(year).title(title)
                .description(description).displayOrder(displayOrder)
                .active(active).image(image)
                .build();

        return ResponseEntity.ok(ApiResponse.success(journeyService.updateMilestone(id, dto, imageFile)));
    }

    @DeleteMapping("/milestones/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMilestone(@PathVariable Long id) {
        journeyService.deleteMilestone(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PatchMapping("/milestones/{id}/toggle")
    public ResponseEntity<ApiResponse<JourneyMilestoneDTO>> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(journeyService.toggleMilestoneActive(id)));
    }

    @PutMapping("/milestones/reorder")
    public ResponseEntity<ApiResponse<List<JourneyMilestoneDTO>>> reorderMilestones(
            @RequestBody List<Long> orderedIds) {
        return ResponseEntity.ok(ApiResponse.success(journeyService.reorderMilestones(orderedIds)));
    }
}