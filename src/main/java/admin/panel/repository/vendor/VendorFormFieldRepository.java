package admin.panel.repository.vendor;

import admin.panel.entity.vendor.VendorFormField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorFormFieldRepository extends JpaRepository<VendorFormField, Long> {
    List<VendorFormField> findByIsActiveTrueOrderByDisplayOrderAsc();
    List<VendorFormField> findAllByOrderByDisplayOrderAsc();
    Optional<VendorFormField> findByFieldKey(String fieldKey);
    boolean existsByFieldKey(String fieldKey);
}