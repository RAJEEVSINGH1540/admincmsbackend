package admin.panel.repository.journeysection;

import admin.panel.entity.journeysection.JourneyConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JourneyConfigRepository extends JpaRepository<JourneyConfig, Long> {
}