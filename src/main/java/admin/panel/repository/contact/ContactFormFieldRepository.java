package admin.panel.repository.contact;

import admin.panel.entity.contact.ContactusFormField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactFormFieldRepository extends JpaRepository<ContactusFormField, Long> {
    List<ContactusFormField> findAllByOrderBySortOrderAsc();
    List<ContactusFormField> findByActiveTrueOrderBySortOrderAsc();
    boolean existsByFieldName(String fieldName);
}