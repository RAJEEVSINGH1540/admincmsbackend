package admin.panel.config;

import admin.panel.entity.media.MediaDetail;
import admin.panel.repository.media.MediaDetailRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(6)
@RequiredArgsConstructor
@Slf4j
public class MediaDetailDataInitializer implements CommandLineRunner {

    private final MediaDetailRepository repository;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() > 0) {
            log.info("✅ Media Detail data already exists. Skipping initialization.");
            return;
        }

        log.info("🔄 Initializing Media Detail default data...");

        // ==================== ARTICLE 1 ====================
        List<String> article1Paragraphs = List.of(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
                "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit."
        );

        List<String> article1BelowParagraphs = List.of(
                "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga.",
                "Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus."
        );

        MediaDetail article1 = MediaDetail.builder()
                .bannerImageUrl("")
                .cardImageUrl("")
                .cardDate("24 Feb 2026")
                .cardDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
                .articleTitle("Heading Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
                .articleParagraphs(objectMapper.writeValueAsString(article1Paragraphs))
                .contentImageUrl("")
                .imageBelowParagraphs(objectMapper.writeValueAsString(article1BelowParagraphs))
                .articleDate("24 Feb 2026")
                .isActive(true)
                .displayOrder(1)
                .build();

        // ==================== ARTICLE 2 ====================
        List<String> article2Paragraphs = List.of(
                "Consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium."
        );

        List<String> article2BelowParagraphs = List.of(
                "Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet.",
                "Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur."
        );

        MediaDetail article2 = MediaDetail.builder()
                .bannerImageUrl("")
                .cardImageUrl("")
                .cardDate("15 Mar 2026")
                .cardDescription("Eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation.")
                .articleTitle("Eiusmod tempor incididunt ut labore et dolore magna aliqua sed do consectetur adipiscing elit.")
                .articleParagraphs(objectMapper.writeValueAsString(article2Paragraphs))
                .contentImageUrl("")
                .imageBelowParagraphs(objectMapper.writeValueAsString(article2BelowParagraphs))
                .articleDate("15 Mar 2026")
                .isActive(true)
                .displayOrder(2)
                .build();

        // ==================== ARTICLE 3 ====================
        List<String> article3Paragraphs = List.of(
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo."
        );

        List<String> article3BelowParagraphs = List.of(
                "Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur.",
                "Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae."
        );

        MediaDetail article3 = MediaDetail.builder()
                .bannerImageUrl("")
                .cardImageUrl("")
                .cardDate("02 Apr 2026")
                .cardDescription("Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.")
                .articleTitle("Heading Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.")
                .articleParagraphs(objectMapper.writeValueAsString(article3Paragraphs))
                .contentImageUrl("")
                .imageBelowParagraphs(objectMapper.writeValueAsString(article3BelowParagraphs))
                .articleDate("02 Apr 2026")
                .isActive(true)
                .displayOrder(3)
                .build();

        // ==================== ARTICLE 4 ====================
        List<String> article4Paragraphs = List.of(
                "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident.",
                "Similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio."
        );

        List<String> article4BelowParagraphs = List.of(
                "Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus.",
                "Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat."
        );

        MediaDetail article4 = MediaDetail.builder()
                .bannerImageUrl("")
                .cardImageUrl("")
                .cardDate("18 May 2026")
                .cardDescription("At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti.")
                .articleTitle("Heading At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum.")
                .articleParagraphs(objectMapper.writeValueAsString(article4Paragraphs))
                .contentImageUrl("")
                .imageBelowParagraphs(objectMapper.writeValueAsString(article4BelowParagraphs))
                .articleDate("18 May 2026")
                .isActive(true)
                .displayOrder(4)
                .build();

        // ==================== ARTICLE 5 ====================
        List<String> article5Paragraphs = List.of(
                "Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem.",
                "Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur."
        );

        List<String> article5BelowParagraphs = List.of(
                "Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur.",
                "Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt."
        );

        MediaDetail article5 = MediaDetail.builder()
                .bannerImageUrl("")
                .cardImageUrl("")
                .cardDate("30 Jun 2026")
                .cardDescription("Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit.")
                .articleTitle("Heading Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet consectetur adipisci velit sed quia non numquam.")
                .articleParagraphs(objectMapper.writeValueAsString(article5Paragraphs))
                .contentImageUrl("")
                .imageBelowParagraphs(objectMapper.writeValueAsString(article5BelowParagraphs))
                .articleDate("30 Jun 2026")
                .isActive(true)
                .displayOrder(5)
                .build();

        // ==================== SAVE ALL ====================
        List<MediaDetail> savedArticles = repository.saveAll(
                List.of(article1, article2, article3, article4, article5)
        );

        log.info("✅ Media Detail initialized with {} articles:", savedArticles.size());
        savedArticles.forEach(article ->
                log.info("   📰 ID: {} | Title: {} | Date: {} | Active: {}",
                        article.getId(),
                        article.getArticleTitle().substring(0, Math.min(50, article.getArticleTitle().length())) + "...",
                        article.getArticleDate(),
                        article.getIsActive()
                )
        );
    }
}