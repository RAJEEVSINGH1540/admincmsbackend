package admin.panel.repository.aboutus;

import admin.panel.entity.aboutus.AboutGlance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

;

@Repository
public interface AboutGlanceRepository extends JpaRepository<AboutGlance, Long> {
    Optional<AboutGlance> findFirstByOrderByIdAsc();
}