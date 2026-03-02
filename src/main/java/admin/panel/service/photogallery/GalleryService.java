package admin.panel.service.photogallery;

import admin.panel.dto.photogallery.GalleryCategoryRequest;
import admin.panel.dto.photogallery.GalleryCategoryResponse;
import admin.panel.entity.photogallery.GalleryCategory;
import admin.panel.repository.photogallery.GalleryCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GalleryService {

    private final GalleryCategoryRepository repository;

    public GalleryService(GalleryCategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<GalleryCategoryResponse> getAllCategories() {
        List<GalleryCategory> categories = repository.findAllByOrderByDisplayOrderAscIdAsc();
        return categories.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GalleryCategoryResponse getCategoryById(Long id) {
        GalleryCategory category = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return toResponse(category);
    }

    @Transactional
    public GalleryCategoryResponse createCategory(GalleryCategoryRequest request) {
        GalleryCategory category = new GalleryCategory();
        category.setTitle(request.getTitle());
        category.setCoverImage(request.getCoverImage());
        category.setDisplayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0);

        List<String> images = new ArrayList<>();
        if (request.getImages() != null) {
            images.addAll(request.getImages());
        }
        category.setImages(images);

        GalleryCategory saved = repository.save(category);
        return toResponse(saved);
    }

    @Transactional
    public GalleryCategoryResponse updateCategory(Long id, GalleryCategoryRequest request) {
        GalleryCategory category = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        category.setTitle(request.getTitle());
        category.setCoverImage(request.getCoverImage());

        if (request.getDisplayOrder() != null) {
            category.setDisplayOrder(request.getDisplayOrder());
        }

        category.getImages().clear();
        if (request.getImages() != null) {
            category.getImages().addAll(request.getImages());
        }

        GalleryCategory saved = repository.save(category);
        return toResponse(saved);
    }

    @Transactional
    public void deleteCategory(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional
    public GalleryCategoryResponse addImage(Long id, String imageUrl) {
        GalleryCategory category = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            category.getImages().add(imageUrl.trim());
        }

        GalleryCategory saved = repository.save(category);
        return toResponse(saved);
    }

    @Transactional
    public GalleryCategoryResponse removeImage(Long id, int index) {
        GalleryCategory category = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        if (index >= 0 && index < category.getImages().size()) {
            category.getImages().remove(index);
        }

        GalleryCategory saved = repository.save(category);
        return toResponse(saved);
    }

    private GalleryCategoryResponse toResponse(GalleryCategory category) {
        GalleryCategoryResponse response = new GalleryCategoryResponse();
        response.setId(category.getId());

        response.setTitle(category.getTitle());
        response.setCoverImage(category.getCoverImage());
        response.setImages(category.getImages());
        response.setDisplayOrder(category.getDisplayOrder());
        return response;
    }
}