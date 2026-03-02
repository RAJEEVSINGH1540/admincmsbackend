package admin.panel.repository.homepage;

import admin.panel.entity.homepage.HomePage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HomePageRepository extends JpaRepository<HomePage, Long> {

    Optional<HomePage> findFirstByOrderByIdAsc();
}