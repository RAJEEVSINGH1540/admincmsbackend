package admin.panel.repository;

import admin.panel.entity.AboutBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutBannerRepository extends JpaRepository<AboutBanner, Long> {
}