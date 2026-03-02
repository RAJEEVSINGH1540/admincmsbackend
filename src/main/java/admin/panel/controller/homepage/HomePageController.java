package admin.panel.controller.homepage;

import admin.panel.dto.ApiResponse;
import admin.panel.dto.HomePageDTO;
import admin.panel.dto.HomePageRequest;
import admin.panel.service.homepage.HomePageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomePageController {

    private final HomePageService homePageService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<HomePageDTO>>> getAll() {
        try {
            List<HomePageDTO> data = homePageService.getAll();
            return ResponseEntity.ok(ApiResponse.success("Home pages fetched", data));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HomePageDTO>> getById(@PathVariable Long id) {
        try {
            HomePageDTO data = homePageService.getById(id);
            return ResponseEntity.ok(ApiResponse.success(data));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<HomePageDTO>> create(
            @RequestParam(value = "heroHeading", required = false, defaultValue = "") String heroHeading,
            @RequestParam(value = "heroParagraph", required = false, defaultValue = "") String heroParagraph,
            @RequestParam(value = "isActive", required = false, defaultValue = "true") Boolean isActive,
            @RequestPart(value = "heroVideo", required = false) MultipartFile heroVideo,
            @RequestPart(value = "contentVideo", required = false) MultipartFile contentVideo
    ) {
        try {
            HomePageRequest request = HomePageRequest.builder()
                    .heroHeading(heroHeading)
                    .heroParagraph(heroParagraph)
                    .isActive(isActive)
                    .build();

            HomePageDTO data = homePageService.create(request, heroVideo, contentVideo);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Home page created", data));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<HomePageDTO>> update(
            @PathVariable Long id,
            @RequestParam(value = "heroHeading", required = false) String heroHeading,
            @RequestParam(value = "heroParagraph", required = false) String heroParagraph,
            @RequestParam(value = "heroVideoUrl", required = false) String heroVideoUrl,
            @RequestParam(value = "contentVideoUrl", required = false) String contentVideoUrl,
            @RequestParam(value = "isActive", required = false, defaultValue = "true") Boolean isActive,
            @RequestPart(value = "heroVideo", required = false) MultipartFile heroVideo,
            @RequestPart(value = "contentVideo", required = false) MultipartFile contentVideo
    ) {
        try {
            HomePageRequest request = HomePageRequest.builder()
                    .heroHeading(heroHeading)
                    .heroParagraph(heroParagraph)
                    .heroVideoUrl(heroVideoUrl)
                    .contentVideoUrl(contentVideoUrl)
                    .isActive(isActive)
                    .build();

            HomePageDTO data = homePageService.update(id, request, heroVideo, contentVideo);
            return ResponseEntity.ok(ApiResponse.success("Home page updated", data));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        try {
            homePageService.delete(id);
            return ResponseEntity.ok(ApiResponse.success("Home page deleted", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}/hero-video")
    public ResponseEntity<ApiResponse<HomePageDTO>> clearHeroVideo(@PathVariable Long id) {
        try {
            HomePageDTO data = homePageService.clearHeroVideo(id);
            return ResponseEntity.ok(ApiResponse.success("Hero video removed", data));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}/content-video")
    public ResponseEntity<ApiResponse<HomePageDTO>> clearContentVideo(@PathVariable Long id) {
        try {
            HomePageDTO data = homePageService.clearContentVideo(id);
            return ResponseEntity.ok(ApiResponse.success("Content video removed", data));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}/hero-text/{field}")
    public ResponseEntity<ApiResponse<HomePageDTO>> clearHeroText(
            @PathVariable Long id,
            @PathVariable String field
    ) {
        try {
            HomePageDTO data = homePageService.clearHeroText(id, field);
            return ResponseEntity.ok(ApiResponse.success("Hero text cleared", data));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}