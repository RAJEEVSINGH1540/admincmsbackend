package admin.panel.repository.aboutus;

import admin.panel.entity.aboutus.AboutVerticals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AboutVerticalsRepository extends JpaRepository<AboutVerticals, Long> {
    Optional<AboutVerticals> findFirstByOrderByIdAsc();
}