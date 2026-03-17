package admin.panel.controller.aboutus;

import admin.panel.dto.ApiResponse;
import admin.panel.dto.aboutus.AboutThinkingBigCmsResponse;
import admin.panel.dto.aboutus.AboutThinkingBigRequest;
import admin.panel.dto.aboutus.AboutThinkingBigResponse;
import admin.panel.dto.aboutus.ParagraphRequest;
import admin.panel.service.aboutus.AboutThinkingBigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/aboutus/thinking")
@RequiredArgsConstructor
public class AboutThinkingBigController {

    private final AboutThinkingBigService service;

    // Public API - returns plain string paragraphs (for live website)
    @GetMapping
    public ResponseEntity<ApiResponse<AboutThinkingBigResponse>> get() {
        return ResponseEntity.ok(ApiResponse.success("Thinking Big fetched", service.get()));
    }

    // CMS API - returns paragraph objects with id, content, sortOrder (for CMS editor)
    @GetMapping("/cms")
    public ResponseEntity<ApiResponse<AboutThinkingBigCmsResponse>> getCms() {
        return ResponseEntity.ok(ApiResponse.success("Thinking Big CMS fetched", service.getCms()));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<AboutThinkingBigCmsResponse>> update(@RequestBody AboutThinkingBigRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Thinking Big updated", service.update(request)));
    }

    @PostMapping("/paragraphs")
    public ResponseEntity<ApiResponse<AboutThinkingBigCmsResponse>> addParagraph(@RequestBody ParagraphRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Paragraph added", service.addParagraph(request)));
    }

    @PutMapping("/paragraphs/{id}")
    public ResponseEntity<ApiResponse<AboutThinkingBigCmsResponse>> updateParagraph(
            @PathVariable Long id, @RequestBody ParagraphRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Paragraph updated", service.updateParagraph(id, request)));
    }

    @DeleteMapping("/paragraphs/{id}")
    public ResponseEntity<ApiResponse<AboutThinkingBigCmsResponse>> deleteParagraph(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Paragraph deleted", service.deleteParagraph(id)));
    }
}