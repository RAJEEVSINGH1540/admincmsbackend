package admin.panel.service.vission;

import admin.panel.dto.vission.VisionPageDTO;
import admin.panel.entity.vission.*;
import admin.panel.repository.vission.VisionPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisionPageService {

    private final VisionPageRepository repository;

    @Transactional(readOnly = true)
    public VisionPageDTO get() {
        return repository.findFirstByOrderByIdAsc()
                .map(this::toDTO)
                .orElse(null);
    }

    @Transactional
    public VisionPageDTO create(VisionPageDTO dto) {
        if (repository.count() > 0) {
            throw new RuntimeException("Vision page already exists. Use update instead.");
        }
        VisionPage page = new VisionPage();
        mapDtoToEntity(dto, page);
        return toDTO(repository.save(page));
    }

    @Transactional
    public VisionPageDTO update(Long id, VisionPageDTO dto) {
        VisionPage page = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vision page not found"));
        mapDtoToEntity(dto, page);
        return toDTO(repository.save(page));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Vision page not found");
        }
        repository.deleteById(id);
    }

    private void mapDtoToEntity(VisionPageDTO dto, VisionPage page) {
        page.setHeroBadgeText(dto.getHeroBadgeText());
        page.setHeroHeading1(dto.getHeroHeading1());
        page.setHeroHighlightWord(dto.getHeroHighlightWord());
        page.setHeroHeading2(dto.getHeroHeading2());
        page.setHeroSubtitle(dto.getHeroSubtitle());
        page.setHeroScrollText(dto.getHeroScrollText());
        page.setVisionLabel(dto.getVisionLabel());
        page.setVisionHeading(dto.getVisionHeading());
        page.setVisionDescription(dto.getVisionDescription());
        page.setMissionLabel(dto.getMissionLabel());
        page.setMissionHeading(dto.getMissionHeading());
        page.setMissionDescription(dto.getMissionDescription());
        page.setCoreValuesBadge(dto.getCoreValuesBadge());
        page.setCoreValuesHeading(dto.getCoreValuesHeading());
        page.setCoreValuesSubtitle(dto.getCoreValuesSubtitle());
        page.setTimelineBadge(dto.getTimelineBadge());
        page.setTimelineHeading(dto.getTimelineHeading());
        page.setTimelineSubtitle(dto.getTimelineSubtitle());
        page.setStatsBadge(dto.getStatsBadge());
        page.setStatsHeading(dto.getStatsHeading());
        page.setStatsSubtitle(dto.getStatsSubtitle());
        page.setCommitmentBadge(dto.getCommitmentBadge());
        page.setCommitmentHeading(dto.getCommitmentHeading());
        page.setCommitmentSubtitle(dto.getCommitmentSubtitle());
        page.setCommitmentQuote(dto.getCommitmentQuote());
        page.setCommitmentQuoteAuthor(dto.getCommitmentQuoteAuthor());
        page.setCtaHeading1(dto.getCtaHeading1());
        page.setCtaHighlightText(dto.getCtaHighlightText());
        page.setCtaDescription(dto.getCtaDescription());
        page.setCtaPrimaryButtonText(dto.getCtaPrimaryButtonText());
        page.setCtaPrimaryButtonLink(dto.getCtaPrimaryButtonLink());
        page.setCtaSecondaryButtonText(dto.getCtaSecondaryButtonText());
        page.setCtaSecondaryButtonLink(dto.getCtaSecondaryButtonLink());
        page.setCtaTrustLabel(dto.getCtaTrustLabel());
        page.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);

        // Vision Pillars
        page.getVisionPillars().clear();
        if (dto.getVisionPillars() != null) {
            dto.getVisionPillars().forEach(p -> page.getVisionPillars().add(
                    VisionPillar.builder().icon(p.getIcon()).text(p.getText())
                            .sortOrder(p.getSortOrder()).visionPage(page).build()));
        }

        // Mission Metrics
        page.getMissionMetrics().clear();
        if (dto.getMissionMetrics() != null) {
            dto.getMissionMetrics().forEach(m -> page.getMissionMetrics().add(
                    MissionMetric.builder().value(m.getValue()).label(m.getLabel())
                            .bgColor(m.getBgColor()).textColor(m.getTextColor()).borderColor(m.getBorderColor())
                            .sortOrder(m.getSortOrder()).visionPage(page).build()));
        }

        // Core Values
        page.getCoreValues().clear();
        if (dto.getCoreValues() != null) {
            dto.getCoreValues().forEach(v -> page.getCoreValues().add(
                    CoreValue.builder().title(v.getTitle()).description(v.getDescription())
                            .iconName(v.getIconName())
                            .accentColorFrom(v.getAccentColorFrom()).accentColorTo(v.getAccentColorTo())
                            .hoverBgColor(v.getHoverBgColor()).hoverBorderColor(v.getHoverBorderColor())
                            .sortOrder(v.getSortOrder()).visionPage(page).build()));
        }

        // Milestones
        page.getMilestones().clear();
        if (dto.getMilestones() != null) {
            dto.getMilestones().forEach(m -> page.getMilestones().add(
                    TimelineMilestone.builder().year(m.getYear()).title(m.getTitle())
                            .description(m.getDescription()).icon(m.getIcon()).side(m.getSide())
                            .sortOrder(m.getSortOrder()).visionPage(page).build()));
        }

        // Stats
        page.getStats().clear();
        if (dto.getStats() != null) {
            dto.getStats().forEach(s -> page.getStats().add(
                    VisionStat.builder().value(s.getValue()).suffix(s.getSuffix()).prefix(s.getPrefix())
                            .label(s.getLabel()).description(s.getDescription()).iconName(s.getIconName())
                            .sortOrder(s.getSortOrder()).visionPage(page).build()));
        }

        // Commitments
        page.getCommitments().clear();
        if (dto.getCommitments() != null) {
            dto.getCommitments().forEach(c -> page.getCommitments().add(
                    Commitment.builder().title(c.getTitle()).description(c.getDescription())
                            .iconName(c.getIconName())
                            .items(c.getItems() != null ? new ArrayList<>(c.getItems()) : new ArrayList<>())
                            .sortOrder(c.getSortOrder()).visionPage(page).build()));
        }

        // Trust Badges
        page.getTrustBadges().clear();
        if (dto.getTrustBadges() != null) {
            dto.getTrustBadges().forEach(t -> page.getTrustBadges().add(
                    TrustBadge.builder().name(t.getName())
                            .sortOrder(t.getSortOrder()).visionPage(page).build()));
        }
    }

    private VisionPageDTO toDTO(VisionPage page) {
        return VisionPageDTO.builder()
                .id(page.getId())
                .heroBadgeText(page.getHeroBadgeText())
                .heroHeading1(page.getHeroHeading1())
                .heroHighlightWord(page.getHeroHighlightWord())
                .heroHeading2(page.getHeroHeading2())
                .heroSubtitle(page.getHeroSubtitle())
                .heroScrollText(page.getHeroScrollText())
                .visionLabel(page.getVisionLabel())
                .visionHeading(page.getVisionHeading())
                .visionDescription(page.getVisionDescription())
                .missionLabel(page.getMissionLabel())
                .missionHeading(page.getMissionHeading())
                .missionDescription(page.getMissionDescription())
                .coreValuesBadge(page.getCoreValuesBadge())
                .coreValuesHeading(page.getCoreValuesHeading())
                .coreValuesSubtitle(page.getCoreValuesSubtitle())
                .timelineBadge(page.getTimelineBadge())
                .timelineHeading(page.getTimelineHeading())
                .timelineSubtitle(page.getTimelineSubtitle())
                .statsBadge(page.getStatsBadge())
                .statsHeading(page.getStatsHeading())
                .statsSubtitle(page.getStatsSubtitle())
                .commitmentBadge(page.getCommitmentBadge())
                .commitmentHeading(page.getCommitmentHeading())
                .commitmentSubtitle(page.getCommitmentSubtitle())
                .commitmentQuote(page.getCommitmentQuote())
                .commitmentQuoteAuthor(page.getCommitmentQuoteAuthor())
                .ctaHeading1(page.getCtaHeading1())
                .ctaHighlightText(page.getCtaHighlightText())
                .ctaDescription(page.getCtaDescription())
                .ctaPrimaryButtonText(page.getCtaPrimaryButtonText())
                .ctaPrimaryButtonLink(page.getCtaPrimaryButtonLink())
                .ctaSecondaryButtonText(page.getCtaSecondaryButtonText())
                .ctaSecondaryButtonLink(page.getCtaSecondaryButtonLink())
                .ctaTrustLabel(page.getCtaTrustLabel())
                .isActive(page.getIsActive())
                .visionPillars(page.getVisionPillars().stream().map(p ->
                        VisionPageDTO.VisionPillarDTO.builder().id(p.getId()).icon(p.getIcon()).text(p.getText()).sortOrder(p.getSortOrder()).build()
                ).collect(Collectors.toList()))
                .missionMetrics(page.getMissionMetrics().stream().map(m ->
                        VisionPageDTO.MissionMetricDTO.builder().id(m.getId()).value(m.getValue()).label(m.getLabel())
                                .bgColor(m.getBgColor()).textColor(m.getTextColor()).borderColor(m.getBorderColor())
                                .sortOrder(m.getSortOrder()).build()
                ).collect(Collectors.toList()))
                .coreValues(page.getCoreValues().stream().map(v ->
                        VisionPageDTO.CoreValueDTO.builder().id(v.getId()).title(v.getTitle()).description(v.getDescription())
                                .iconName(v.getIconName())
                                .accentColorFrom(v.getAccentColorFrom()).accentColorTo(v.getAccentColorTo())
                                .hoverBgColor(v.getHoverBgColor()).hoverBorderColor(v.getHoverBorderColor())
                                .sortOrder(v.getSortOrder()).build()
                ).collect(Collectors.toList()))
                .milestones(page.getMilestones().stream().map(m ->
                        VisionPageDTO.TimelineMilestoneDTO.builder().id(m.getId()).year(m.getYear()).title(m.getTitle())
                                .description(m.getDescription()).icon(m.getIcon()).side(m.getSide()).sortOrder(m.getSortOrder()).build()
                ).collect(Collectors.toList()))
                .stats(page.getStats().stream().map(s ->
                        VisionPageDTO.VisionStatDTO.builder().id(s.getId()).value(s.getValue()).suffix(s.getSuffix()).prefix(s.getPrefix())
                                .label(s.getLabel()).description(s.getDescription()).iconName(s.getIconName()).sortOrder(s.getSortOrder()).build()
                ).collect(Collectors.toList()))
                .commitments(page.getCommitments().stream().map(c ->
                        VisionPageDTO.CommitmentDTO.builder().id(c.getId()).title(c.getTitle()).description(c.getDescription())
                                .iconName(c.getIconName()).items(c.getItems() != null ? new ArrayList<>(c.getItems()) : new ArrayList<>())
                                .sortOrder(c.getSortOrder()).build()
                ).collect(Collectors.toList()))
                .trustBadges(page.getTrustBadges().stream().map(t ->
                        VisionPageDTO.TrustBadgeDTO.builder().id(t.getId()).name(t.getName()).sortOrder(t.getSortOrder()).build()
                ).collect(Collectors.toList()))
                .build();
    }
}