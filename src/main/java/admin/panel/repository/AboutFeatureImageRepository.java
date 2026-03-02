package admin.panel.repository;

import admin.panel.entity.AboutFeatureImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutFeatureImageRepository extends JpaRepository<AboutFeatureImage, Long> {
}