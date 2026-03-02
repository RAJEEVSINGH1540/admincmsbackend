package admin.panel.repository.vendorsuplier;

import admin.panel.entity.vendorsuplier.VendorUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorUserRepository extends JpaRepository<VendorUser, Long> {

    Optional<VendorUser> findByEmail(String email);

    Optional<VendorUser> findByEmailVerificationToken(String token);

    Optional<VendorUser> findByPasswordResetToken(String token);

    Optional<VendorUser> findByRefreshToken(String refreshToken);

    boolean existsByEmail(String email);

    Page<VendorUser> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<VendorUser> findByIsEmailVerifiedFalseOrderByCreatedAtDesc(Pageable pageable);

    long countByIsEmailVerifiedFalse();
}