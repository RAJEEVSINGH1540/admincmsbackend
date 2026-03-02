package admin.panel.repository.mediaandevents;

import admin.panel.entity.mediaandevents.MediaEventsPage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MediaEventsPageRepository extends JpaRepository<MediaEventsPage, Long> {
    Optional<MediaEventsPage> findFirstByOrderByIdAsc();
}