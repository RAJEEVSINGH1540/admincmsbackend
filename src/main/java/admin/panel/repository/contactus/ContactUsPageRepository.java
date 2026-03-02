package admin.panel.repository.contactus;

import admin.panel.entity.contactus.ContactUsPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactUsPageRepository extends JpaRepository<ContactUsPage, Long> {

    @Query("SELECT c FROM ContactUsPage c WHERE c.isActive = true")
    Optional<ContactUsPage> findActiveContactPage();
}