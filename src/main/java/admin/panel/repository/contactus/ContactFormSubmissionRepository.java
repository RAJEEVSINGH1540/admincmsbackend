package admin.panel.repository.contactus;

import admin.panel.entity.contactus.ContactFormSubmission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactFormSubmissionRepository extends JpaRepository<ContactFormSubmission, Long> {

    Page<ContactFormSubmission> findAllByOrderBySubmittedAtDesc(Pageable pageable);

    Page<ContactFormSubmission> findByIsReadFalseOrderBySubmittedAtDesc(Pageable pageable);

    long countByIsReadFalse();
}