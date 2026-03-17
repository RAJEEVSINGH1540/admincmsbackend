package admin.panel.repository.contact;

import admin.panel.entity.contact.ContactPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactPageRepository extends JpaRepository<ContactPage, Long> {
    Optional<ContactPage> findFirstByOrderByIdAsc();
}