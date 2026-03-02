package admin.panel.config;

import admin.panel.entity.*;
import admin.panel.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final AboutBannerRepository bannerRepository;
    private final AboutContentImageRepository contentImageRepository;
    private final AboutFeatureImageRepository featureImageRepository;
    private final AboutBulletSectionRepository bulletSectionRepository;

    @Override
    public void run(String... args) {

        // Only seed if database is empty
        if (bannerRepository.count() > 0) {
            log.info("Database already has data. Skipping initialization.");
            return;
        }

        log.info("Initializing default About page data...");

        // ==================== BANNER ====================
        bannerRepository.save(AboutBanner.builder()
                .image("https://images.unsplash.com/photo-1504307651254-35680f356dfd?w=1200&h=600&fit=crop")
                .heading("Colossal means extremely large or great and that is what we represent!")
                .paragraph("At Colossal, we contribute to the growth of industry by providing efficient and reliable solutions. Our commitment to delivering top-tier quality sets us apart. With a diverse range of services tailored to meet the evolving needs of our clients, we have established ourselves as a trusted partner across multiple sectors.\n\nWe provide fully integrated solutions to our clients by designing, constructing and maintaining key systems. Projects executed for major international companies across diverse industries demonstrate our commitment to excellence. Our approach combines innovation with proven methodologies to deliver results that consistently meet and exceed expectations.")
                .build());

        // ==================== CONTENT IMAGE (Quote Banner) ====================
        contentImageRepository.save(AboutContentImage.builder()
                .image("https://images.unsplash.com/photo-1473448912268-2022ce9509d8?w=1200&h=600&fit=crop")
                .paragraph("Our Vision, Mission and Core Values keep us driven and motivated. We look forward to continuing to innovate and exceed our customers' expectations.")
                .build());

        // ==================== FEATURE IMAGE ====================
        featureImageRepository.save(AboutFeatureImage.builder()
                .image("https://images.unsplash.com/photo-1460925895917-afdab827c52f?w=1200&h=600&fit=crop")
                .build());

        // ==================== SECTION 1 (Structural Fabrication) ====================
        AboutBulletSection section1 = AboutBulletSection.builder()
                .sectionKey("section1")
                .image("https://images.unsplash.com/photo-1504307651254-35680f356dfd?w=800&h=600&fit=crop")
                .heading("STRUCTURAL FABRICATION")
                .bullets(new ArrayList<>(List.of(
                        "Pre-assembly of the fabricated pipe spool to module",
                        "Specializing in structural steel fabrication",
                        "Industrial strength alloy steel work since 2005",
                        "Pre-fabrication and fit up",
                        "Pipe prefabrication using carbon steel and stainless steel",
                        "Multi-national joint ventures",
                        "Large scale offshore installations",
                        "ASME-approved and certified for all duties",
                        "Flood barricading",
                        "Renewable energy infrastructure",
                        "Steel modelling and engineering drawings"
                )))
                .build();
        bulletSectionRepository.save(section1);

        // ==================== SECTION 2 (Process Piping) ====================
        AboutBulletSection section2 = AboutBulletSection.builder()
                .sectionKey("section2")
                .image("https://images.unsplash.com/photo-1581094794329-c8112a89af12?w=800&h=600&fit=crop")
                .heading("PROCESS PIPING")
                .bullets(new ArrayList<>(List.of(
                        "Detailed scope of piping system design",
                        "Installation of piping structures",
                        "Testing and certification",
                        "All-field industrial application welding",
                        "Piping modification and assembly",
                        "Valve installation",
                        "Flange management and maintenance",
                        "Hydrostatic testing",
                        "Hot tapping and line stopping"
                )))
                .build();
        bulletSectionRepository.save(section2);

        log.info("Default About page data initialized successfully!");
    }
}