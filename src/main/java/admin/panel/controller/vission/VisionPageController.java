package admin.panel.controller.vission;

import admin.panel.dto.ApiResponse;
import admin.panel.dto.vission.VisionPageDTO;
import admin.panel.service.vission.VisionPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vision")
@RequiredArgsConstructor
public class VisionPageController {

    private final VisionPageService service;

    @GetMapping
    public ResponseEntity<ApiResponse<VisionPageDTO>> get() {
        VisionPageDTO dto = service.get();
        if (dto == null) {
            return ResponseEntity.ok(ApiResponse.success(null));
        }
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<VisionPageDTO>> create(@RequestBody VisionPageDTO dto) {
        return ResponseEntity.ok(ApiResponse.success(service.create(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VisionPageDTO>> update(@PathVariable Long id, @RequestBody VisionPageDTO dto) {
        return ResponseEntity.ok(ApiResponse.success(service.update(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}