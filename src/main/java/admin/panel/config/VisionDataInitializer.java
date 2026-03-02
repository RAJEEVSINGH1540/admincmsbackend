package admin.panel.config;

import admin.panel.entity.vission.*;
import admin.panel.repository.vission.VisionPageRepository;
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
@Order(2)
public class VisionDataInitializer implements CommandLineRunner {

    private final VisionPageRepository visionPageRepository;

    @Override
    public void run(String... args) {
        if (visionPageRepository.count() > 0) {
            log.info("Vision page data already exists. Skipping initialization.");
            return;
        }

        log.info("Initializing default Vision page data...");

        VisionPage page = VisionPage.builder()
                .heroBadgeText("Our Vision & Purpose")
                .heroHeading1("Building the")
                .heroHighlightWord("Future")
                .heroHeading2("Today")
                .heroSubtitle("We envision a world where every structure stands as a testament to human ingenuity, sustainable innovation, and uncompromising quality that endures for generations.")
                .heroScrollText("Discover Our Vision")
                .visionLabel("Our Vision")
                .visionHeading("Redefining Skylines Across the Nation")
                .visionDescription("At Colossal Construction, we see beyond blueprints. We envision thriving communities, landmark structures, and infrastructure that powers progress. Our vision is to be the most trusted and innovative construction partner in the industry—transforming ideas into iconic realities that stand the test of time.")
                .missionLabel("Our Mission")
                .missionHeading("Excellence in Every Detail")
                .missionDescription("To deliver world-class construction services with unwavering commitment to safety, quality, and innovation. We build not just structures, but trust—empowering our clients, communities, and teams to achieve extraordinary results.")
                .coreValuesBadge("The Foundation We Build On")
                .coreValuesHeading("Our Core Values")
                .coreValuesSubtitle("These aren't just words on a wall. These are the principles that guide every decision, every project, and every relationship at Colossal Construction.")
                .timelineBadge("Our Journey")
                .timelineHeading("Milestones That Define Us")
                .timelineSubtitle("From humble beginnings to industry leadership—every milestone represents our relentless pursuit of excellence and innovation.")
                .statsBadge("Numbers That Speak")
                .statsHeading("Impact in Numbers")
                .statsSubtitle("Our track record speaks volumes. These numbers reflect years of dedication, expertise, and unwavering commitment to excellence.")
                .commitmentBadge("Our Commitments")
                .commitmentHeading("Promises We Keep")
                .commitmentSubtitle("Our commitments aren't aspirational—they're operational. We hold ourselves accountable to every stakeholder, every day.")
                .commitmentQuote("Our greatest construction isn't made of steel and concrete—it's the trust we build with every handshake, every deadline met, and every promise delivered.")
                .commitmentQuoteAuthor("— Leadership Team, Colossal Construction")
                .ctaHeading1("Ready to Build")
                .ctaHighlightText("Something Extraordinary?")
                .ctaDescription("Whether it's a towering commercial complex, sustainable infrastructure, or your dream project—let's make it happen together.")
                .ctaPrimaryButtonText("Start Your Project")
                .ctaPrimaryButtonLink("#contact")
                .ctaSecondaryButtonText("View Our Portfolio")
                .ctaSecondaryButtonLink("#gallery")
                .ctaTrustLabel("Trusted & Certified By")
                .isActive(true)
                .visionPillars(new ArrayList<>())
                .missionMetrics(new ArrayList<>())
                .coreValues(new ArrayList<>())
                .milestones(new ArrayList<>())
                .stats(new ArrayList<>())
                .commitments(new ArrayList<>())
                .trustBadges(new ArrayList<>())
                .build();

        // Pillars
        List.of(
                VisionPillar.builder().icon("🏗️").text("Pioneer innovative construction methodologies").sortOrder(0).visionPage(page).build(),
                VisionPillar.builder().icon("🌱").text("Champion sustainable building practices").sortOrder(1).visionPage(page).build(),
                VisionPillar.builder().icon("🤝").text("Foster lasting partnerships with every stakeholder").sortOrder(2).visionPage(page).build()
        ).forEach(page.getVisionPillars()::add);

        // Mission Metrics — now hex colors
        List.of(
                MissionMetric.builder().value("Zero").label("Compromise on Safety").bgColor("#f0fdf4").textColor("#15803d").borderColor("#bbf7d0").sortOrder(0).visionPage(page).build(),
                MissionMetric.builder().value("100%").label("Client Satisfaction Goal").bgColor("#eff6ff").textColor("#1d4ed8").borderColor("#bfdbfe").sortOrder(1).visionPage(page).build(),
                MissionMetric.builder().value("ISO").label("Certified Processes").bgColor("#faf5ff").textColor("#7e22ce").borderColor("#e9d5ff").sortOrder(2).visionPage(page).build(),
                MissionMetric.builder().value("LEED").label("Sustainable Standards").bgColor("#fffbeb").textColor("#b45309").borderColor("#fde68a").sortOrder(3).visionPage(page).build()
        ).forEach(page.getMissionMetrics()::add);

        // Core Values — now hex colors
        List.of(
                CoreValue.builder().title("Integrity").description("We operate with transparency and honesty in every project, contract, and relationship. Our word is our bond.")
                        .iconName("shield").accentColorFrom("#3b82f6").accentColorTo("#1d4ed8").hoverBgColor("#eff6ff").hoverBorderColor("#bfdbfe").sortOrder(0).visionPage(page).build(),
                CoreValue.builder().title("Excellence").description("We set the highest standards and pursue them relentlessly. Mediocrity has no place in our work.")
                        .iconName("star").accentColorFrom("#f59e0b").accentColorTo("#b45309").hoverBgColor("#fffbeb").hoverBorderColor("#fde68a").sortOrder(1).visionPage(page).build(),
                CoreValue.builder().title("Innovation").description("We embrace cutting-edge technology and creative problem-solving to push the boundaries of what's possible.")
                        .iconName("bulb").accentColorFrom("#a855f7").accentColorTo("#7e22ce").hoverBgColor("#faf5ff").hoverBorderColor("#e9d5ff").sortOrder(2).visionPage(page).build(),
                CoreValue.builder().title("Sustainability").description("We build with the future in mind—reducing environmental impact while creating structures that endure.")
                        .iconName("globe").accentColorFrom("#22c55e").accentColorTo("#15803d").hoverBgColor("#f0fdf4").hoverBorderColor("#bbf7d0").sortOrder(3).visionPage(page).build(),
                CoreValue.builder().title("Safety First").description("Every worker goes home safe. We maintain the highest safety protocols and zero-tolerance for shortcuts.")
                        .iconName("alert").accentColorFrom("#f43f5e").accentColorTo("#be123c").hoverBgColor("#fff1f2").hoverBorderColor("#fecdd3").sortOrder(4).visionPage(page).build(),
                CoreValue.builder().title("Collaboration").description("Great structures are built by great teams. We foster collaboration across every level of our organization.")
                        .iconName("users").accentColorFrom("#06b6d4").accentColorTo("#0e7490").hoverBgColor("#ecfeff").hoverBorderColor("#a5f3fc").sortOrder(5).visionPage(page).build()
        ).forEach(page.getCoreValues()::add);

        // Timeline
        List.of(
                TimelineMilestone.builder().year("2005").title("The Foundation").description("Colossal Construction was founded with a vision to redefine quality standards in the construction industry. Starting with a team of 12 dedicated professionals.").icon("🏁").side("left").sortOrder(0).visionPage(page).build(),
                TimelineMilestone.builder().year("2010").title("First Landmark Project").description("Completed our first major commercial complex—a 25-story mixed-use development that set new benchmarks for urban construction in the region.").icon("🏢").side("right").sortOrder(1).visionPage(page).build(),
                TimelineMilestone.builder().year("2014").title("Sustainability Pledge").description("Adopted green building practices across all projects, achieving our first LEED Platinum certification and committing to carbon-neutral operations by 2030.").icon("🌿").side("left").sortOrder(2).visionPage(page).build(),
                TimelineMilestone.builder().year("2018").title("National Expansion").description("Expanded operations to 15 states with over 500 employees, delivering infrastructure projects worth $2 billion in cumulative value.").icon("🚀").side("right").sortOrder(3).visionPage(page).build(),
                TimelineMilestone.builder().year("2022").title("Technology Integration").description("Pioneered the use of AI-powered project management, BIM modeling, and drone-based site monitoring across all active construction sites.").icon("🤖").side("left").sortOrder(4).visionPage(page).build(),
                TimelineMilestone.builder().year("2025").title("The Vision Ahead").description("Targeting $5 billion in project portfolio, 1000+ team members, and industry leadership in sustainable construction technologies.").icon("🎯").side("right").sortOrder(5).visionPage(page).build()
        ).forEach(page.getMilestones()::add);

        // Stats
        List.of(
                VisionStat.builder().value(500).suffix("+").prefix("").label("Projects Completed").description("Successfully delivered across 15+ states").iconName("building").sortOrder(0).visionPage(page).build(),
                VisionStat.builder().value(2).suffix("B+").prefix("$").label("Project Value").description("Total cumulative project portfolio value").iconName("dollar").sortOrder(1).visionPage(page).build(),
                VisionStat.builder().value(850).suffix("+").prefix("").label("Team Members").description("Skilled professionals and growing").iconName("users").sortOrder(2).visionPage(page).build(),
                VisionStat.builder().value(98).suffix("%").prefix("").label("On-Time Delivery").description("Industry-leading project completion rate").iconName("clock").sortOrder(3).visionPage(page).build()
        ).forEach(page.getStats()::add);

        // Commitments
        Commitment c1 = Commitment.builder().title("To Our Clients").description("We promise transparent communication, on-time delivery, and structures that exceed expectations. Your vision is our blueprint.").iconName("user")
                .items(new ArrayList<>(List.of("Dedicated project managers for every engagement", "Real-time project dashboards and updates", "Post-completion warranty and support", "Transparent budgeting with no hidden costs")))
                .sortOrder(0).visionPage(page).build();
        Commitment c2 = Commitment.builder().title("To Our Communities").description("Every project we undertake strengthens the fabric of the community. We build responsibly, hire locally, and contribute meaningfully.").iconName("globe")
                .items(new ArrayList<>(List.of("Local workforce development programs", "Community infrastructure contributions", "Environmental impact assessments", "Neighborhood engagement initiatives")))
                .sortOrder(1).visionPage(page).build();
        Commitment c3 = Commitment.builder().title("To Our People").description("Our team is our greatest asset. We invest in their growth, safety, and well-being because great people build great things.").iconName("heart")
                .items(new ArrayList<>(List.of("Comprehensive safety training programs", "Career advancement opportunities", "Competitive benefits and compensation", "Mental health and wellness support")))
                .sortOrder(2).visionPage(page).build();
        page.getCommitments().addAll(List.of(c1, c2, c3));

        // Trust Badges
        List.of(
                TrustBadge.builder().name("ISO 9001").sortOrder(0).visionPage(page).build(),
                TrustBadge.builder().name("ISO 14001").sortOrder(1).visionPage(page).build(),
                TrustBadge.builder().name("OHSAS 18001").sortOrder(2).visionPage(page).build(),
                TrustBadge.builder().name("LEED Certified").sortOrder(3).visionPage(page).build(),
                TrustBadge.builder().name("BBB A+").sortOrder(4).visionPage(page).build()
        ).forEach(page.getTrustBadges()::add);

        visionPageRepository.save(page);
        log.info("Default Vision page data initialized successfully!");
    }
}