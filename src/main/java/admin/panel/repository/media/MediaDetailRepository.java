package admin.panel.repository.media;

import admin.panel.entity.media.MediaDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaDetailRepository extends JpaRepository<MediaDetail, Long> {

    List<MediaDetail> findByIsActiveTrueOrderByDisplayOrderAscCreatedAtDesc();

    List<MediaDetail> findByIdNotAndIsActiveTrueOrderByCreatedAtDesc(Long id);

    List<MediaDetail> findAllByOrderByDisplayOrderAscCreatedAtDesc();
}