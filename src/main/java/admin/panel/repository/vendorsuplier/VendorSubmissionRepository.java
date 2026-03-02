package admin.panel.repository.vendorsuplier;

import admin.panel.entity.vendorsuplier.VendorSubmission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorSubmissionRepository extends JpaRepository<VendorSubmission, Long> {

    Page<VendorSubmission> findAllByOrderBySubmittedAtDesc(Pageable pageable);

    Page<VendorSubmission> findByIsReadFalseOrderBySubmittedAtDesc(Pageable pageable);

    long countByIsReadFalse();
}