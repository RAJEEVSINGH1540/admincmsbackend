package admin.panel.controller.mediaandevents;

import admin.panel.dto.ApiResponse;
import admin.panel.dto.mediaandevents.MediaEventsPageDTO;
import admin.panel.service.mediaandevents.MediaEventsPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/media-events")
@RequiredArgsConstructor
public class MediaEventsPageController {

    private final MediaEventsPageService service;

    @GetMapping
    public ResponseEntity<ApiResponse<MediaEventsPageDTO>> get() {
        return ResponseEntity.ok(ApiResponse.success(service.get()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MediaEventsPageDTO>> create(@RequestBody MediaEventsPageDTO dto) {
        return ResponseEntity.ok(ApiResponse.success(service.create(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MediaEventsPageDTO>> update(@PathVariable Long id, @RequestBody MediaEventsPageDTO dto) {
        return ResponseEntity.ok(ApiResponse.success(service.update(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}