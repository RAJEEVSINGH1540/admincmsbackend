package admin.panel.repository.aboutus;

import admin.panel.entity.aboutus.AboutThinkingBig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AboutThinkingBigRepository extends JpaRepository<AboutThinkingBig, Long> {
    Optional<AboutThinkingBig> findFirstByOrderByIdAsc();
}