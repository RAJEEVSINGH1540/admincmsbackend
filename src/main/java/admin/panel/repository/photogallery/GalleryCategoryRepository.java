package admin.panel.repository.photogallery;

import admin.panel.entity.photogallery.GalleryCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GalleryCategoryRepository extends JpaRepository<GalleryCategory, Long> {
    List<GalleryCategory> findAllByOrderByDisplayOrderAscIdAsc();
}