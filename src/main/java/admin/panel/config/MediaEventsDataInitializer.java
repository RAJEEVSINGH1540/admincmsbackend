package admin.panel.config;

import admin.panel.entity.mediaandevents.*;
import admin.panel.repository.mediaandevents.MediaEventsPageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(3)
public class MediaEventsDataInitializer implements CommandLineRunner {

    private final MediaEventsPageRepository repository;

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            log.info("Media & Events page already exists. Skipping.");
            return;
        }

        log.info("Initializing Media & Events page data...");

        MediaEventsPage page = MediaEventsPage.builder()
            .heroBadgeText("Media & Events")
            .heroHeading1("In the")
            .heroHighlightWord("Spotlight")
            .heroSubtitle("Stay informed with our latest news, upcoming events, industry awards, and media coverage. Colossal Construction continues to shape the future of construction.")
            .heroScrollText("Explore Updates")
            .pressBadge("Latest News")
            .pressHeading("Press Releases")
            .pressSubtitle("Official announcements, project milestones, and corporate news from Colossal Construction.")
            .eventsBadge("Mark Your Calendar")
            .eventsHeading("Upcoming Events")
            .eventsSubtitle("Join us at industry conferences, groundbreaking ceremonies, and community engagement programs.")
            .awardsBadge("Recognition")
            .awardsHeading("Awards & Achievements")
            .awardsSubtitle("Our commitment to excellence has been recognized by leading industry organizations worldwide.")
            .coverageBadge("In The News")
            .coverageHeading("Media Coverage")
            .coverageSubtitle("See what leading publications and news outlets are saying about Colossal Construction.")
            .ctaHeading("Stay")
            .ctaHighlightText("Connected")
            .ctaDescription("Subscribe to our newsletter for the latest updates, project announcements, and industry insights delivered straight to your inbox.")
            .ctaButtonText("Subscribe Now")
            .ctaPlaceholder("Enter your email address")
            .isActive(true)
            .pressReleases(new ArrayList<>())
            .events(new ArrayList<>())
            .awards(new ArrayList<>())
            .coverages(new ArrayList<>())
            .build();

        // Press Releases
        List.of(
            PressRelease.builder().title("Colossal Construction Secures $500M Highway Infrastructure Contract")
                .summary("Colossal Construction has been awarded a landmark $500 million contract for the development of a 120-kilometer highway corridor, reinforcing its position as a leader in large-scale infrastructure projects.")
                .date("December 15, 2024").source("Construction Weekly").sourceUrl("#")
                .imageUrl("https://images.unsplash.com/photo-1504307651254-35680f356dfd?w=600&h=400&fit=crop")
                .category("Infrastructure").tagColor("#3b82f6").sortOrder(0).mediaEventsPage(page).build(),
            PressRelease.builder().title("New Sustainable Building Division Launched")
                .summary("In response to growing demand for eco-friendly construction, Colossal Construction announces the launch of its dedicated Green Building Division, targeting LEED Platinum certifications across all new projects.")
                .date("November 28, 2024").source("Green Builder Magazine").sourceUrl("#")
                .imageUrl("https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?w=600&h=400&fit=crop")
                .category("Sustainability").tagColor("#22c55e").sortOrder(1).mediaEventsPage(page).build(),
            PressRelease.builder().title("Strategic Partnership with Global Engineering Firm Announced")
                .summary("Colossal Construction partners with Meridian Engineering Group to deliver next-generation smart building solutions across the Middle East and North Africa region.")
                .date("October 10, 2024").source("Business Times").sourceUrl("#")
                .imageUrl("https://images.unsplash.com/photo-1460925895917-afdab827c52f?w=600&h=400&fit=crop")
                .category("Partnership").tagColor("#a855f7").sortOrder(2).mediaEventsPage(page).build()
        ).forEach(page.getPressReleases()::add);

        // Events
        List.of(
            CompanyEvent.builder().title("Annual Construction Innovation Summit 2025")
                .description("Join 500+ industry leaders for keynote presentations, panel discussions, and live demonstrations of cutting-edge construction technologies and sustainable building practices.")
                .date("March 15-17, 2025").time("9:00 AM - 6:00 PM").location("Muscat Convention Center, Oman")
                .imageUrl("https://images.unsplash.com/photo-1540575467063-178a50c2df87?w=600&h=400&fit=crop")
                .status("upcoming").statusColor("#3b82f6").registerUrl("#contact").sortOrder(0).mediaEventsPage(page).build(),
            CompanyEvent.builder().title("Groundbreaking Ceremony — Marina Bay Tower")
                .description("Be part of history as we break ground on the 45-story Marina Bay Tower, our most ambitious mixed-use development project to date.")
                .date("April 5, 2025").time("10:00 AM").location("Marina Bay District, Dubai")
                .imageUrl("https://images.unsplash.com/photo-1504307651254-35680f356dfd?w=600&h=400&fit=crop")
                .status("upcoming").statusColor("#f59e0b").registerUrl("#contact").sortOrder(1).mediaEventsPage(page).build(),
            CompanyEvent.builder().title("Community Safety Workshop Series")
                .description("Free workshops for local communities covering construction site safety awareness, emergency preparedness, and sustainable living practices.")
                .date("Ongoing — Monthly").time("2:00 PM - 5:00 PM").location("Multiple Locations")
                .imageUrl("https://images.unsplash.com/photo-1531482615713-2afd69097998?w=600&h=400&fit=crop")
                .status("ongoing").statusColor("#22c55e").registerUrl("#contact").sortOrder(2).mediaEventsPage(page).build()
        ).forEach(page.getEvents()::add);

        // Awards
        List.of(
            Award.builder().title("Excellence in Sustainable Construction")
                .description("Recognized for pioneering green building practices and achieving LEED Platinum certification across 15 commercial projects.")
                .year("2024").organization("World Construction Awards")
                .imageUrl("https://images.unsplash.com/photo-1567427017947-545c5f8d16ad?w=300&h=300&fit=crop")
                .accentColor("#f59e0b").sortOrder(0).mediaEventsPage(page).build(),
            Award.builder().title("Best Infrastructure Project of the Year")
                .description("Awarded for the successful completion of the Eastern Expressway, a 85-kilometer highway project delivered 3 months ahead of schedule.")
                .year("2024").organization("National Construction Federation")
                .imageUrl("https://images.unsplash.com/photo-1504307651254-35680f356dfd?w=300&h=300&fit=crop")
                .accentColor("#3b82f6").sortOrder(1).mediaEventsPage(page).build(),
            Award.builder().title("Safety Excellence Award")
                .description("Achieved 2 million man-hours without a single lost-time incident across all active project sites.")
                .year("2023").organization("International Safety Council")
                .imageUrl("https://images.unsplash.com/photo-1581094794329-c8112a89af12?w=300&h=300&fit=crop")
                .accentColor("#22c55e").sortOrder(2).mediaEventsPage(page).build(),
            Award.builder().title("Employer of the Year")
                .description("Recognized for outstanding employee welfare programs, career development initiatives, and workplace culture.")
                .year("2023").organization("HR Excellence Awards")
                .imageUrl("https://images.unsplash.com/photo-1531482615713-2afd69097998?w=300&h=300&fit=crop")
                .accentColor("#a855f7").sortOrder(3).mediaEventsPage(page).build()
        ).forEach(page.getAwards()::add);

        // Media Coverage
        List.of(
            MediaCoverage.builder().outlet("Construction Weekly").logoUrl("")
                .title("How Colossal Construction Is Redefining Infrastructure in the GCC")
                .excerpt("An in-depth look at the company's innovative approach to large-scale infrastructure development and their commitment to sustainable practices...")
                .articleUrl("#").date("Dec 2024").sortOrder(0).mediaEventsPage(page).build(),
            MediaCoverage.builder().outlet("Business Times").logoUrl("")
                .title("Top 10 Construction Companies to Watch in 2025")
                .excerpt("Colossal Construction ranks among the top construction firms driving innovation, with a project portfolio exceeding $2 billion...")
                .articleUrl("#").date("Nov 2024").sortOrder(1).mediaEventsPage(page).build(),
            MediaCoverage.builder().outlet("Green Builder Magazine").logoUrl("")
                .title("The Future of Sustainable Building: A Conversation with Colossal's CEO")
                .excerpt("An exclusive interview exploring the company's ambitious sustainability roadmap and carbon-neutral construction goals...")
                .articleUrl("#").date("Oct 2024").sortOrder(2).mediaEventsPage(page).build()
        ).forEach(page.getCoverages()::add);

        repository.save(page);
        log.info("Media & Events page initialized with {} press, {} events, {} awards, {} coverages",
            page.getPressReleases().size(), page.getEvents().size(), page.getAwards().size(), page.getCoverages().size());
    }
}