package admin.panel.repository.vendor;

import admin.panel.entity.vendor.VendorVerificationStatus;
import admin.panel.entity.vendor.VendorUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorUserRepository extends JpaRepository<VendorUser, Long> {
    Optional<VendorUser> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<VendorUser> findByEmailVerificationToken(String token);
    Optional<VendorUser> findByPasswordResetToken(String token);
    List<VendorUser> findByVerificationStatusOrderByCreatedAtDesc(VendorVerificationStatus status);
    List<VendorUser> findAllByOrderByCreatedAtDesc();
    long countByVerificationStatus(VendorVerificationStatus status);
    long countByIsEmailVerified(Boolean isEmailVerified);
}