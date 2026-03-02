package admin.panel.repository.vendorsuplier;

import admin.panel.entity.vendorsuplier.VendorSupplierForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorFormRepository extends JpaRepository<VendorSupplierForm, Long> {
    List<VendorSupplierForm> findAllByOrderByCreatedAtDesc();
}