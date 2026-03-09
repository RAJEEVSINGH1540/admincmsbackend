package admin.panel.config;

import admin.panel.entity.AboutBanner;
import admin.panel.entity.AboutBulletSection;
import admin.panel.entity.AboutContentImage;
import admin.panel.entity.AboutFeatureImage;
import admin.panel.entity.journeysection.JourneyConfig;
import admin.panel.entity.journeysection.JourneyMilestone;
import admin.panel.repository.AboutBannerRepository;
import admin.panel.repository.AboutBulletSectionRepository;
import admin.panel.repository.AboutContentImageRepository;
import admin.panel.repository.AboutFeatureImageRepository;
import admin.panel.repository.journeysection.JourneyConfigRepository;
import admin.panel.repository.journeysection.JourneyMilestoneRepository;
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
    private final JourneyConfigRepository journeyConfigRepository;
    private final JourneyMilestoneRepository journeyMilestoneRepository;

    @Override
    public void run(String... args) {

        // ========== ABOUT PAGE ==========
        if (bannerRepository.count() == 0) {
            log.info("Initializing default About page data...");

            bannerRepository.save(AboutBanner.builder()
                    .image("https://images.unsplash.com/photo-1504307651254-35680f356dfd?w=1200&h=600&fit=crop")
                    .heading("Colossal means extremely large or great and that is what we represent!")
                    .paragraph("At Colossal, we contribute to the growth of industry by providing efficient and reliable solutions. Our commitment to delivering top-tier quality sets us apart. With a diverse range of services tailored to meet the evolving needs of our clients, we have established ourselves as a trusted partner across multiple sectors.\n\nWe provide fully integrated solutions to our clients by designing, constructing and maintaining key systems. Projects executed for major international companies across diverse industries demonstrate our commitment to excellence.")
                    .build());

            contentImageRepository.save(AboutContentImage.builder()
                    .image("https://images.unsplash.com/photo-1473448912268-2022ce9509d8?w=1200&h=600&fit=crop")
                    .paragraph("Our Vision, Mission and Core Values keep us driven and motivated. We look forward to continuing to innovate and exceed our customers' expectations.")
                    .build());

            featureImageRepository.save(AboutFeatureImage.builder()
                    .image("https://images.unsplash.com/photo-1460925895917-afdab827c52f?w=1200&h=600&fit=crop")
                    .build());

            bulletSectionRepository.save(AboutBulletSection.builder().sectionKey("section1")
                    .image("https://images.unsplash.com/photo-1504307651254-35680f356dfd?w=800&h=600&fit=crop")
                    .heading("STRUCTURAL FABRICATION")
                    .bullets(new ArrayList<>(List.of("Pre-assembly of the fabricated pipe spool to module", "Specializing in structural steel fabrication", "Industrial strength alloy steel work since 2005", "Pre-fabrication and fit up", "Pipe prefabrication using carbon steel and stainless steel", "Multi-national joint ventures", "Large scale offshore installations", "ASME-approved and certified for all duties", "Flood barricading", "Renewable energy infrastructure", "Steel modelling and engineering drawings")))
                    .build());

            bulletSectionRepository.save(AboutBulletSection.builder().sectionKey("section2")
                    .image("https://images.unsplash.com/photo-1581094794329-c8112a89af12?w=800&h=600&fit=crop")
                    .heading("PROCESS PIPING")
                    .bullets(new ArrayList<>(List.of("Detailed scope of piping system design", "Installation of piping structures", "Testing and certification", "All-field industrial application welding", "Piping modification and assembly", "Valve installation", "Flange management and maintenance", "Hydrostatic testing", "Hot tapping and line stopping")))
                    .build());

            log.info("About page data initialized!");
        }

        // ========== JOURNEY (DESCENDING) ==========
        if (journeyMilestoneRepository.count() == 0) {
            log.info("Initializing Journey data...");

            journeyConfigRepository.save(JourneyConfig.builder()
                    .sectionHeading("COMPLETED PROJECTS")
                    .sectionSubtext("A proven track record of delivering world-class infrastructure projects across the Sultanate of Oman.")
                    .backgroundImage("https://images.unsplash.com/photo-1504307651254-35680f356dfd?w=1920&h=1080&fit=crop")
                    .build());

            journeyMilestoneRepository.save(JourneyMilestone.builder().era("2022–2023").year("2022").title("REINFORCEMENT OF AL SHARQIYA WATER TRANSMISSION SYSTEM AL-8B").description("Client: OWWSC (DIAM) | Main Contractor: CGCINT-HEICO. Construction of DN 700 DI pipeline along with civil ancillaries for total length of 17 km. Pipeline: DN 700 | Material: Ductile Iron | Commenced: July 2022 | Completed: Feb 2023 | Completion: 100%").image("https://images.unsplash.com/photo-1541888946425-d81bb19240f5?w=1200&h=600&fit=crop").displayOrder(1).active(true).build());
            journeyMilestoneRepository.save(JourneyMilestone.builder().era("2021–2022").year("2021").title("REINFORCEMENT OF AL SHARQIYA AL-03").description("Client: DIAM | Main Contractor: CGCINT-HEICO. Installation of DN 1200 DI pipes for transmission pipeline length of 35 km. | Commenced: Aug 2021 | Completed: Nov 2022 | Completion: 100%").image("https://images.unsplash.com/photo-1504307651254-35680f356dfd?w=1200&h=600&fit=crop").displayOrder(2).active(true).build());
            journeyMilestoneRepository.save(JourneyMilestone.builder().era("2021–2022").year("2021").title("DAKHILYAH WATER TRANSMISSION PROJECT").description("Client: PAEW | Main Contractor: L&T Oman. Pipeline: 68\" & 64\" Dia for 30 KM | Material: Carbon Steel | Commenced: Feb 2021 | Completed: Jan 2022 | HSE: 2,500 Man-hours, Zero LTIs | Completion: 100%").image("https://images.unsplash.com/photo-1581094794329-c8112a89af12?w=1200&h=600&fit=crop").displayOrder(3).active(true).build());
            journeyMilestoneRepository.save(JourneyMilestone.builder().era("2021–2022").year("2021").title("SEWER WORKS AT SUMAIL INDUSTRIAL CITY").description("Client: MADYAN | Main Contractor: Road Line. Pipeline: 355 mm HDPE - 2.7km, 350 mm HDPE - 4.5 km | Commenced: May 2021 | Completed: March 2022 | Completion: 100%").image("https://images.unsplash.com/photo-1590674899484-d5640e854abe?w=1200&h=600&fit=crop").displayOrder(4).active(true).build());
            journeyMilestoneRepository.save(JourneyMilestone.builder().era("2020–2021").year("2020").title("EXTENSION OF WATER NETWORKS AL AMERAT - PHASE A").description("Client: PAW | Main Contractor: Colossal Engineering. Pipeline: 16\" to 1\" dia | Material: DI & HDPE | Commenced: Nov 2020 | Completed: Jul 2021 | HSE: 10,500 Man-hours, Zero LTIs | Completion: 100%").image("https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?w=1200&h=600&fit=crop").displayOrder(5).active(true).build());
            journeyMilestoneRepository.save(JourneyMilestone.builder().era("2020–2021").year("2020").title("DUQM REFINERY EPC 1 – PACKAGE 1").description("Client: Duqm Refinery | Main Contractors: TR–Daewoo JV. Pipeline: 96\" to ½\" Dia | Material: CS & SS | Commenced: Apr 2020 | Completed: Apr 2021 | HSE: 125,500 Man-hours, Zero LTIs | Completion: 100%").image("https://images.unsplash.com/photo-1565008447742-97f6f38c985c?w=1200&h=600&fit=crop").displayOrder(6).active(true).build());
            journeyMilestoneRepository.save(JourneyMilestone.builder().era("2019–2020").year("2019").title("UPGRADATION OF BARKA PUMPING STATION").description("Client: PAEW | Main Contractor: Towell Infrastructure. Pipeline: 80\" to 56\" Dia | Material: CS & DI | Commenced: Aug 2019 | Completed: Feb 2020 | HSE: 65,000 Man-hours, Zero LTIs | Completion: 100%").image("https://images.unsplash.com/photo-1581094794329-c8112a89af12?w=1200&h=600&fit=crop").displayOrder(7).active(true).build());
            journeyMilestoneRepository.save(JourneyMilestone.builder().era("2019–2020").year("2019").title("DESIGN, SUPPLY & INSTALL PORTABLE WATER NETWORK").description("Client: Majis Industrial | Main Contractor: Colossal Engineering. Pipeline: 400-600 mm DI & HDPE PN 16 | Commenced: Jun 2019 | Completed: Nov 2019 | HSE: 31,000 Man-hours, Zero LTIs | Completion: 100%").image("https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?w=1200&h=600&fit=crop").displayOrder(8).active(true).build());
            journeyMilestoneRepository.save(JourneyMilestone.builder().era("2018–2019").year("2018").title("BAWSHER TO SEEB WATER TRANSMISSION PIPELINE").description("Client: PAEW | Main Contractor: L&T Oman. Pipeline: 64\" Dia for 10 KM | Material: Carbon Steel | Commenced: Jan 2018 | Completed: Jul 2019 | HSE: 280,000 Man-hours, Zero LTIs | Completion: 100%").image("https://images.unsplash.com/photo-1504307651254-35680f356dfd?w=1200&h=600&fit=crop").displayOrder(9).active(true).build());
            journeyMilestoneRepository.save(JourneyMilestone.builder().era("2018–2019").year("2018").title("RHIP OFF PLOT PROJECT – FLOW LINE").description("Client: PDO | Main Contractor: Al Turki Enterprises. Pipeline: 6\" Dia - 25 KM | Material: Carbon Steel | Commenced: Feb 2018 | Completed: Aug 2018 | HSE: 82,500 Man-hours, Zero LTIs | Completion: 100%").image("https://images.unsplash.com/photo-1504307651254-35680f356dfd?w=1200&h=600&fit=crop").displayOrder(10).active(true).build());
            journeyMilestoneRepository.save(JourneyMilestone.builder().era("2018–2019").year("2018").title("LIWA EPC3-SMP2(U1) PIPING WORKS").description("Client: ORPIC - LIWA Plastic | Main Contractor: BEC. Pipeline: 48\" to ¾\" Dia | Material: CS & SS | Commenced: Jan 2018 | Completed: Jan 2019 | HSE: 222,000 Man-hours, Zero LTIs | Completion: 100%").image("https://images.unsplash.com/photo-1460925895917-afdab827c52f?w=1200&h=600&fit=crop").displayOrder(11).active(true).build());
            journeyMilestoneRepository.save(JourneyMilestone.builder().era("2016–2017").year("2017").title("CCCW PIPING PROJECT").description("Client: Salalah IPP | Contractors: SEPCO III / Towell Engineering. Pipeline: 20\" to 2\" Dia | Material: Carbon Steel | Commenced: Apr 2017 | Completed: Jul 2017 | HSE: 15,500 Man-hours, Zero LTIs | Completion: 100%").image("https://images.unsplash.com/photo-1590674899484-d5640e854abe?w=1200&h=600&fit=crop").displayOrder(12).active(true).build());
            journeyMilestoneRepository.save(JourneyMilestone.builder().era("2016–2017").year("2016").title("AMAL 1C PLANT – HRSG").description("Client: PDO | Main Contractor: STS LLC. Pipeline: 32\" to 0.5\" Dia | Material: CS & SS | Commenced: Jun 2016 | Completed: Jan 2017 | HSE: 58,500 Man-hours, Zero LTIs | Completion: 100%").image("https://images.unsplash.com/photo-1497366216548-37526070297c?w=1200&h=600&fit=crop").displayOrder(13).active(true).build());
            journeyMilestoneRepository.save(JourneyMilestone.builder().era("2016–2017").year("2016").title("QURIYAT TIE IN").description("Client: PAEW | Main Contractor: Elecnor–Target JV. Pipeline: 56\" Dia - 13 KM | Material: Carbon Steel | Commenced: Oct 2016 | Completed: Jul 2017 | HSE: 300,000 Man-hours, Zero LTIs | Completion: 100%").image("https://images.unsplash.com/photo-1541888946425-d81bb19240f5?w=1200&h=600&fit=crop").displayOrder(14).active(true).build());
            journeyMilestoneRepository.save(JourneyMilestone.builder().era("2016–2017").year("2016").title("SOHAR REFINERY IMPROVEMENT – SRIP").description("Client: ORPIC | Main Contractors: Daelim–Petrofac JV / BEC. Pipeline: 32\" to 0.5\" Dia | Material: Carbon Steel | Commenced: Mar 2016 | Completed: Sep 2016 | HSE: 252,000 Man-hours, Zero LTIs | Completion: 100%").image("https://images.unsplash.com/photo-1565008447742-97f6f38c985c?w=1200&h=600&fit=crop").displayOrder(15).active(true).build());
            journeyMilestoneRepository.save(JourneyMilestone.builder().era("2015–2016").year("2015").title("OMAN CONVENTION & EXHIBITION CENTRE – OCEC").description("Client: OTDC | Main Contractors: Carillion Alawi / BEC. Pipeline: 20\" to 0.5\" Dia | Material: Carbon Steel | Commenced: Feb 2015 | Completed: Jul 2016 | HSE: 85,650 Man-hours, Zero LTIs | Completion: 100%").image("https://images.unsplash.com/photo-1541888946425-d81bb19240f5?w=1200&h=600&fit=crop").displayOrder(16).active(true).build());
            journeyMilestoneRepository.save(JourneyMilestone.builder().era("2015–2016").year("2015").title("MUSCAT INTERNATIONAL AIRPORT DEVELOPMENT").description("Client: OAMC | Main Contractor: Joannou & Paraskevaides. Pipeline: 28\" to 12\" Dia | Material: DI & CS | Commenced: Dec 2015 | Completed: Apr 2016 | HSE: 32,500 Man-hours, Zero LTIs | Completion: 100%").image("https://images.unsplash.com/photo-1581094794329-c8112a89af12?w=1200&h=600&fit=crop").displayOrder(17).active(true).build());
            journeyMilestoneRepository.save(JourneyMilestone.builder().era("2015–2016").year("2015").title("INNOVATION PARK MUSCAT").description("Client: The Research Council | MEP Contractor: Airmech Oman. Pipeline: 32\" to 8\" Dia | Material: Carbon Steel | Commenced: Nov 2015 | Completed: Aug 2016 | HSE: 35,500 Man-hours, Zero LTIs | Completion: 100%").image("https://images.unsplash.com/photo-1460925895917-afdab827c52f?w=1200&h=600&fit=crop").displayOrder(18).active(true).build());

            log.info("Journey data initialized — 18 projects.");
        }

        }
    }
