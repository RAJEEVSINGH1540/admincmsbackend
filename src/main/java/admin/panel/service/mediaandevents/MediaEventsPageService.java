package admin.panel.service.mediaandevents;

import admin.panel.dto.mediaandevents.MediaEventsPageDTO;
import admin.panel.entity.mediaandevents.*;
import admin.panel.repository.mediaandevents.MediaEventsPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MediaEventsPageService {

    private final MediaEventsPageRepository repository;

    @Transactional(readOnly = true)
    public MediaEventsPageDTO get() {
        return repository.findFirstByOrderByIdAsc().map(this::toDTO).orElse(null);
    }

    @Transactional
    public MediaEventsPageDTO create(MediaEventsPageDTO dto) {
        if (repository.count() > 0) throw new RuntimeException("Media & Events page already exists.");
        MediaEventsPage page = new MediaEventsPage();
        mapDtoToEntity(dto, page);
        return toDTO(repository.save(page));
    }

    @Transactional
    public MediaEventsPageDTO update(Long id, MediaEventsPageDTO dto) {
        MediaEventsPage page = repository.findById(id).orElseThrow(() -> new RuntimeException("Page not found"));
        mapDtoToEntity(dto, page);
        return toDTO(repository.save(page));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new RuntimeException("Page not found");
        repository.deleteById(id);
    }

    private void mapDtoToEntity(MediaEventsPageDTO dto, MediaEventsPage p) {
        p.setHeroBadgeText(dto.getHeroBadgeText());
        p.setHeroHeading1(dto.getHeroHeading1());
        p.setHeroHighlightWord(dto.getHeroHighlightWord());
        p.setHeroSubtitle(dto.getHeroSubtitle());
        p.setHeroScrollText(dto.getHeroScrollText());
        p.setPressBadge(dto.getPressBadge());
        p.setPressHeading(dto.getPressHeading());
        p.setPressSubtitle(dto.getPressSubtitle());
        p.setEventsBadge(dto.getEventsBadge());
        p.setEventsHeading(dto.getEventsHeading());
        p.setEventsSubtitle(dto.getEventsSubtitle());
        p.setAwardsBadge(dto.getAwardsBadge());
        p.setAwardsHeading(dto.getAwardsHeading());
        p.setAwardsSubtitle(dto.getAwardsSubtitle());
        p.setCoverageBadge(dto.getCoverageBadge());
        p.setCoverageHeading(dto.getCoverageHeading());
        p.setCoverageSubtitle(dto.getCoverageSubtitle());
        p.setCtaHeading(dto.getCtaHeading());
        p.setCtaHighlightText(dto.getCtaHighlightText());
        p.setCtaDescription(dto.getCtaDescription());
        p.setCtaButtonText(dto.getCtaButtonText());
        p.setCtaPlaceholder(dto.getCtaPlaceholder());
        p.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);

        p.getPressReleases().clear();
        if (dto.getPressReleases() != null) {
            dto.getPressReleases().forEach(d -> p.getPressReleases().add(
                PressRelease.builder().title(d.getTitle()).summary(d.getSummary()).date(d.getDate())
                    .source(d.getSource()).sourceUrl(d.getSourceUrl()).imageUrl(d.getImageUrl())
                    .category(d.getCategory()).tagColor(d.getTagColor()).sortOrder(d.getSortOrder())
                    .mediaEventsPage(p).build()));
        }

        p.getEvents().clear();
        if (dto.getEvents() != null) {
            dto.getEvents().forEach(d -> p.getEvents().add(
                CompanyEvent.builder().title(d.getTitle()).description(d.getDescription()).date(d.getDate())
                    .time(d.getTime()).location(d.getLocation()).imageUrl(d.getImageUrl())
                    .status(d.getStatus()).statusColor(d.getStatusColor()).registerUrl(d.getRegisterUrl())
                    .sortOrder(d.getSortOrder()).mediaEventsPage(p).build()));
        }

        p.getAwards().clear();
        if (dto.getAwards() != null) {
            dto.getAwards().forEach(d -> p.getAwards().add(
                Award.builder().title(d.getTitle()).description(d.getDescription()).year(d.getYear())
                    .organization(d.getOrganization()).imageUrl(d.getImageUrl()).accentColor(d.getAccentColor())
                    .sortOrder(d.getSortOrder()).mediaEventsPage(p).build()));
        }

        p.getCoverages().clear();
        if (dto.getCoverages() != null) {
            dto.getCoverages().forEach(d -> p.getCoverages().add(
                MediaCoverage.builder().outlet(d.getOutlet()).logoUrl(d.getLogoUrl()).title(d.getTitle())
                    .excerpt(d.getExcerpt()).articleUrl(d.getArticleUrl()).date(d.getDate())
                    .sortOrder(d.getSortOrder()).mediaEventsPage(p).build()));
        }
    }

    private MediaEventsPageDTO toDTO(MediaEventsPage p) {
        return MediaEventsPageDTO.builder()
            .id(p.getId())
            .heroBadgeText(p.getHeroBadgeText()).heroHeading1(p.getHeroHeading1())
            .heroHighlightWord(p.getHeroHighlightWord()).heroSubtitle(p.getHeroSubtitle())
            .heroScrollText(p.getHeroScrollText())
            .pressBadge(p.getPressBadge()).pressHeading(p.getPressHeading()).pressSubtitle(p.getPressSubtitle())
            .eventsBadge(p.getEventsBadge()).eventsHeading(p.getEventsHeading()).eventsSubtitle(p.getEventsSubtitle())
            .awardsBadge(p.getAwardsBadge()).awardsHeading(p.getAwardsHeading()).awardsSubtitle(p.getAwardsSubtitle())
            .coverageBadge(p.getCoverageBadge()).coverageHeading(p.getCoverageHeading()).coverageSubtitle(p.getCoverageSubtitle())
            .ctaHeading(p.getCtaHeading()).ctaHighlightText(p.getCtaHighlightText())
            .ctaDescription(p.getCtaDescription()).ctaButtonText(p.getCtaButtonText()).ctaPlaceholder(p.getCtaPlaceholder())
            .isActive(p.getIsActive())
            .pressReleases(p.getPressReleases().stream().map(r -> MediaEventsPageDTO.PressReleaseDTO.builder()
                .id(r.getId()).title(r.getTitle()).summary(r.getSummary()).date(r.getDate())
                .source(r.getSource()).sourceUrl(r.getSourceUrl()).imageUrl(r.getImageUrl())
                .category(r.getCategory()).tagColor(r.getTagColor()).sortOrder(r.getSortOrder()).build()
            ).collect(Collectors.toList()))
            .events(p.getEvents().stream().map(e -> MediaEventsPageDTO.CompanyEventDTO.builder()
                .id(e.getId()).title(e.getTitle()).description(e.getDescription()).date(e.getDate())
                .time(e.getTime()).location(e.getLocation()).imageUrl(e.getImageUrl())
                .status(e.getStatus()).statusColor(e.getStatusColor()).registerUrl(e.getRegisterUrl())
                .sortOrder(e.getSortOrder()).build()
            ).collect(Collectors.toList()))
            .awards(p.getAwards().stream().map(a -> MediaEventsPageDTO.AwardDTO.builder()
                .id(a.getId()).title(a.getTitle()).description(a.getDescription()).year(a.getYear())
                .organization(a.getOrganization()).imageUrl(a.getImageUrl()).accentColor(a.getAccentColor())
                .sortOrder(a.getSortOrder()).build()
            ).collect(Collectors.toList()))
            .coverages(p.getCoverages().stream().map(c -> MediaEventsPageDTO.MediaCoverageDTO.builder()
                .id(c.getId()).outlet(c.getOutlet()).logoUrl(c.getLogoUrl()).title(c.getTitle())
                .excerpt(c.getExcerpt()).articleUrl(c.getArticleUrl()).date(c.getDate())
                .sortOrder(c.getSortOrder()).build()
            ).collect(Collectors.toList()))
            .build();
    }
}