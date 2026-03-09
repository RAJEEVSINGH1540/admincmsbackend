package admin.panel.repository.journeysection;

import admin.panel.entity.journeysection.JourneyMilestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JourneyMilestoneRepository extends JpaRepository<JourneyMilestone, Long> {

    List<JourneyMilestone> findAllByOrderByDisplayOrderAsc();

    List<JourneyMilestone> findByActiveTrueOrderByDisplayOrderAsc();

    boolean existsByEraAndIdNot(String era, Long id);
}