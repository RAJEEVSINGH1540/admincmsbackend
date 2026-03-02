package admin.panel.repository;

import admin.panel.entity.AboutContentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutContentImageRepository extends JpaRepository<AboutContentImage, Long> {
}