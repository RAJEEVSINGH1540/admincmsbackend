package admin.panel.repository.aboutus;

import admin.panel.entity.aboutus.AboutThinkingParagraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutThinkingParagraphRepository extends JpaRepository<AboutThinkingParagraph, Long> {
}