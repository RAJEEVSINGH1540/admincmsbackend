package admin.panel.repository;

import admin.panel.entity.AboutBulletSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AboutBulletSectionRepository extends JpaRepository<AboutBulletSection, Long> {
    Optional<AboutBulletSection> findBySectionKey(String sectionKey);
    List<AboutBulletSection> findAllByOrderByIdAsc();
}