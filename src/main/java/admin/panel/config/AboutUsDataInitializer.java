package admin.panel.config;

import admin.panel.entity.aboutus.AboutGlance;
import admin.panel.entity.aboutus.AboutThinkingBig;
import admin.panel.entity.aboutus.AboutThinkingParagraph;
import admin.panel.entity.aboutus.AboutVerticals;
import admin.panel.repository.aboutus.AboutGlanceRepository;
import admin.panel.repository.aboutus.AboutThinkingBigRepository;
import admin.panel.repository.aboutus.AboutVerticalsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(10)
@RequiredArgsConstructor
@Slf4j
public class AboutUsDataInitializer implements CommandLineRunner {

    private final AboutGlanceRepository glanceRepository;
    private final AboutThinkingBigRepository thinkingBigRepository;
    private final AboutVerticalsRepository verticalsRepository;

    @Override
    public void run(String... args) {
        initGlance();
        initThinkingBig();
        initVerticals();
    }

    private void initGlance() {
        if (glanceRepository.count() == 0) {
            AboutGlance glance = AboutGlance.builder()
                    .title("Colosol At A GLANCE")
                    .description("Building the infrastructure of tomorrow with the precision of today.")
                    .imageUrl(null)
                    .build();
            glanceRepository.save(glance);
            log.info("✅ About Glance initialized");
        } else {
            log.info("ℹ️ About Glance already exists");
        }
    }

    private void initThinkingBig() {
        if (thinkingBigRepository.count() == 0) {
            AboutThinkingBig thinkingBig = AboutThinkingBig.builder()
                    .title("Thinking Big, Doing Things Differently.")
                    .build();

            AboutThinkingParagraph p1 = AboutThinkingParagraph.builder()
                    .content("At Colossal, we believe that success comes from a relentless focus on innovation and delivering unrivalled services. We strive to over-deliver on expectations through a customer-centric approach that gives our clients a competitive edge.")
                    .sortOrder(0)
                    .build();

            AboutThinkingParagraph p2 = AboutThinkingParagraph.builder()
                    .content("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                    .sortOrder(1)
                    .build();

            AboutThinkingParagraph p3 = AboutThinkingParagraph.builder()
                    .content("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                    .sortOrder(2)
                    .build();

            thinkingBig.addParagraph(p1);
            thinkingBig.addParagraph(p2);
            thinkingBig.addParagraph(p3);

            thinkingBigRepository.save(thinkingBig);
            log.info("✅ About Thinking Big initialized with 3 paragraphs");
        } else {
            log.info("ℹ️ About Thinking Big already exists");
        }
    }

    private void initVerticals() {
        if (verticalsRepository.count() == 0) {
            AboutVerticals verticals = AboutVerticals.builder()
                    .title("We are actively involved in three main business verticals, namely")
                    .image1Url(null)
                    .image1Alt("Heavy Machinery and Team")
                    .image2Url(null)
                    .image2Alt("Worker applying coating")
                    .image3Url(null)
                    .image3Alt("Large Pipe Installation")
                    .build();
            verticalsRepository.save(verticals);
            log.info("✅ About Verticals initialized");
        } else {
            log.info("ℹ️ About Verticals already exists");
        }
    }
}