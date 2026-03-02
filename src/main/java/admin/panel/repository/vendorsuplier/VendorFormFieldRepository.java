package admin.panel.repository.vendorsuplier;

import admin.panel.entity.vendorsuplier.VendorFormField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorFormFieldRepository extends JpaRepository<VendorFormField, Long> {
}