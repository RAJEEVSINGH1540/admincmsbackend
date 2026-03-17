package admin.panel.repository.contact;

import admin.panel.entity.contact.ContactSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactSubmissionRepository extends JpaRepository<ContactSubmission, Long> {
    List<admin.panel.entity.contact.ContactSubmission> findAllByOrderBySubmittedAtDesc();
    long countByIsReadFalse();
}