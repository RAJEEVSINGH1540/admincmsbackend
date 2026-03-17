package admin.panel.repository.contact;

import admin.panel.entity.contact.ContactUsTopicOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactTopicOptionRepository extends JpaRepository<ContactUsTopicOption, Long> {
    List<ContactUsTopicOption> findAllByOrderBySortOrderAsc();
    List<ContactUsTopicOption> findByActiveTrueOrderBySortOrderAsc();
}