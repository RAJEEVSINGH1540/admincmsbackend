package admin.panel.repository.vission;

import admin.panel.entity.vission.VisionPage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisionPageRepository extends JpaRepository<VisionPage, Long> {
    Optional<VisionPage> findFirstByOrderByIdAsc();
}