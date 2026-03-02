package admin.panel.service;

import admin.panel.dto.*;
import admin.panel.entity.*;
import admin.panel.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AboutService {

    private final AboutBannerRepository bannerRepository;
    private final AboutContentImageRepository contentImageRepository;
    private final AboutFeatureImageRepository featureImageRepository;
    private final AboutBulletSectionRepository bulletSectionRepository;

    @Transactional(readOnly = true)
    public AboutPageResponse getAboutPageData() {
        AboutBanner banner = bannerRepository.findAll().stream().findFirst().orElse(null);
        AboutContentImage contentImage = contentImageRepository.findAll().stream().findFirst().orElse(null);
        AboutFeatureImage featureImage = featureImageRepository.findAll().stream().findFirst().orElse(null);
        List<AboutBulletSection> allSections = bulletSectionRepository.findAllByOrderByIdAsc();

        return AboutPageResponse.builder()
                .banner(banner != null ? AboutPageResponse.BannerData.builder()
                        .id(banner.getId())
                        .image(banner.getImage())
                        .heading(banner.getHeading())
                        .paragraph(banner.getParagraph())
                        .heading2(banner.getHeading2())
                        .paragraph2(banner.getParagraph2())
                        .build() : null)
                .contentImage(contentImage != null ? AboutPageResponse.ContentImageData.builder()
                        .id(contentImage.getId())
                        .image(contentImage.getImage())
                        .paragraph(contentImage.getParagraph())
                        .heading2(contentImage.getHeading2())
                        .paragraph2(contentImage.getParagraph2())
                        .build() : null)
                .featureImage(featureImage != null ? AboutPageResponse.FeatureImageData.builder()
                        .id(featureImage.getId())
                        .image(featureImage.getImage())
                        .build() : null)
                .sections(allSections.stream().map(s -> AboutPageResponse.BulletSectionData.builder()
                        .id(s.getId())
                        .sectionKey(s.getSectionKey())
                        .image(s.getImage())
                        .heading(s.getHeading())
                        .bullets(s.getBullets())
                        .build()).toList())
                .build();
    }

    @Transactional
    public AboutPageResponse.BannerData updateBanner(BannerRequest request) {
        AboutBanner banner = bannerRepository.findAll().stream().findFirst()
                .orElse(new AboutBanner());
        banner.setImage(request.getImage());
        banner.setHeading(request.getHeading());
        banner.setParagraph(request.getParagraph());
        banner.setHeading2(request.getHeading2());
        banner.setParagraph2(request.getParagraph2());
        AboutBanner saved = bannerRepository.save(banner);
        return AboutPageResponse.BannerData.builder()
                .id(saved.getId()).image(saved.getImage()).heading(saved.getHeading())
                .paragraph(saved.getParagraph()).heading2(saved.getHeading2())
                .paragraph2(saved.getParagraph2()).build();
    }

    @Transactional
    public void clearBannerField(String field) {
        AboutBanner banner = bannerRepository.findAll().stream().findFirst().orElse(null);
        if (banner == null) return;
        switch (field) {
            case "image" -> banner.setImage("");
            case "heading" -> banner.setHeading("");
            case "paragraph" -> banner.setParagraph("");
            case "heading2" -> banner.setHeading2("");
            case "paragraph2" -> banner.setParagraph2("");
            case "all" -> { banner.setImage(""); banner.setHeading(""); banner.setParagraph(""); banner.setHeading2(""); banner.setParagraph2(""); }
        }
        bannerRepository.save(banner);
    }

    @Transactional
    public AboutPageResponse.ContentImageData updateContentImage(ContentImageRequest request) {
        AboutContentImage ci = contentImageRepository.findAll().stream().findFirst().orElse(new AboutContentImage());
        ci.setImage(request.getImage());
        ci.setParagraph(request.getParagraph());
        ci.setHeading2(request.getHeading2());
        ci.setParagraph2(request.getParagraph2());
        AboutContentImage saved = contentImageRepository.save(ci);
        return AboutPageResponse.ContentImageData.builder()
                .id(saved.getId()).image(saved.getImage()).paragraph(saved.getParagraph())
                .heading2(saved.getHeading2()).paragraph2(saved.getParagraph2()).build();
    }

    @Transactional
    public void clearContentImageField(String field) {
        AboutContentImage ci = contentImageRepository.findAll().stream().findFirst().orElse(null);
        if (ci == null) return;
        switch (field) {
            case "image" -> ci.setImage("");
            case "paragraph" -> ci.setParagraph("");
            case "heading2" -> ci.setHeading2("");
            case "paragraph2" -> ci.setParagraph2("");
            case "all" -> { ci.setImage(""); ci.setParagraph(""); ci.setHeading2(""); ci.setParagraph2(""); }
        }
        contentImageRepository.save(ci);
    }

    @Transactional
    public AboutPageResponse.FeatureImageData updateFeatureImage(FeatureImageRequest request) {
        AboutFeatureImage fi = featureImageRepository.findAll().stream().findFirst().orElse(new AboutFeatureImage());
        fi.setImage(request.getImage());
        AboutFeatureImage saved = featureImageRepository.save(fi);
        return AboutPageResponse.FeatureImageData.builder().id(saved.getId()).image(saved.getImage()).build();
    }

    @Transactional
    public void clearFeatureImage() {
        AboutFeatureImage fi = featureImageRepository.findAll().stream().findFirst().orElse(null);
        if (fi == null) return;
        fi.setImage("");
        featureImageRepository.save(fi);
    }

    @Transactional
    public AboutPageResponse.BulletSectionData updateBulletSection(String sectionKey, BulletSectionRequest request) {
        AboutBulletSection section = bulletSectionRepository.findBySectionKey(sectionKey)
                .orElse(AboutBulletSection.builder().sectionKey(sectionKey).bullets(new ArrayList<>()).build());
        section.setImage(request.getImage());
        section.setHeading(request.getHeading());
        section.getBullets().clear();
        if (request.getBullets() != null) section.getBullets().addAll(request.getBullets());
        AboutBulletSection saved = bulletSectionRepository.save(section);
        return AboutPageResponse.BulletSectionData.builder()
                .id(saved.getId()).sectionKey(saved.getSectionKey()).image(saved.getImage())
                .heading(saved.getHeading()).bullets(saved.getBullets()).build();
    }

    @Transactional
    public AboutPageResponse.BulletSectionData createNewSection(String sectionKey, BulletSectionRequest request) {
        AboutBulletSection section = AboutBulletSection.builder()
                .sectionKey(sectionKey).image(request.getImage()).heading(request.getHeading())
                .bullets(request.getBullets() != null ? new ArrayList<>(request.getBullets()) : new ArrayList<>())
                .build();
        AboutBulletSection saved = bulletSectionRepository.save(section);
        return AboutPageResponse.BulletSectionData.builder()
                .id(saved.getId()).sectionKey(saved.getSectionKey()).image(saved.getImage())
                .heading(saved.getHeading()).bullets(saved.getBullets()).build();
    }

    @Transactional
    public void deleteSection(String sectionKey) {
        bulletSectionRepository.findBySectionKey(sectionKey).ifPresent(bulletSectionRepository::delete);
    }

    @Transactional
    public void clearBulletSectionField(String sectionKey, String field) {
        AboutBulletSection section = bulletSectionRepository.findBySectionKey(sectionKey).orElse(null);
        if (section == null) return;
        switch (field) {
            case "image" -> section.setImage("");
            case "heading" -> section.setHeading("");
            case "bullets" -> section.getBullets().clear();
            case "all" -> { section.setImage(""); section.setHeading(""); section.getBullets().clear(); }
        }
        bulletSectionRepository.save(section);
    }

    @Transactional
    public void deleteBulletPoint(String sectionKey, int index) {
        AboutBulletSection section = bulletSectionRepository.findBySectionKey(sectionKey).orElse(null);
        if (section == null || index < 0 || index >= section.getBullets().size()) return;
        section.getBullets().remove(index);
        bulletSectionRepository.save(section);
    }

    @Transactional
    public void addBulletPoint(String sectionKey, String bullet) {
        AboutBulletSection section = bulletSectionRepository.findBySectionKey(sectionKey).orElse(null);
        if (section == null || bullet == null || bullet.trim().isEmpty()) return;
        section.getBullets().add(bullet.trim());
        bulletSectionRepository.save(section);
    }

    @Transactional(readOnly = true)
    public List<AboutPageResponse.BulletSectionData> getAllSections() {
        return bulletSectionRepository.findAllByOrderByIdAsc().stream()
                .map(s -> AboutPageResponse.BulletSectionData.builder()
                        .id(s.getId())
                        .sectionKey(s.getSectionKey())
                        .image(s.getImage())
                        .heading(s.getHeading())
                        .bullets(s.getBullets())
                        .build())
                .toList();
    }
}