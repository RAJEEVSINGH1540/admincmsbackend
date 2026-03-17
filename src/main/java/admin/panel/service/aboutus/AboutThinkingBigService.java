package admin.panel.service.aboutus;

import admin.panel.dto.aboutus.*;
import admin.panel.entity.aboutus.AboutThinkingBig;
import admin.panel.entity.aboutus.AboutThinkingParagraph;
import admin.panel.repository.aboutus.AboutThinkingBigRepository;
import admin.panel.repository.aboutus.AboutThinkingParagraphRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AboutThinkingBigService {

    private final AboutThinkingBigRepository thinkingBigRepository;
    private final AboutThinkingParagraphRepository paragraphRepository;

    // ==================== PUBLIC (Live Website) ====================
    public AboutThinkingBigResponse get() {
        AboutThinkingBig entity = thinkingBigRepository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new RuntimeException("Thinking Big data not found"));
        return mapToPublicResponse(entity);
    }

    // ==================== CMS (Admin Panel) ====================
    public AboutThinkingBigCmsResponse getCms() {
        AboutThinkingBig entity = thinkingBigRepository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new RuntimeException("Thinking Big data not found"));
        return mapToCmsResponse(entity);
    }

    @Transactional
    public AboutThinkingBigCmsResponse update(AboutThinkingBigRequest request) {
        AboutThinkingBig entity = thinkingBigRepository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new RuntimeException("Thinking Big data not found"));

        if (request.getTitle() != null) {
            entity.setTitle(request.getTitle());
        }

        if (request.getParagraphs() != null) {
            Set<Long> incomingIds = request.getParagraphs().stream()
                    .map(ParagraphRequest::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            entity.getParagraphs().removeIf(p -> !incomingIds.contains(p.getId()));

            for (int i = 0; i < request.getParagraphs().size(); i++) {
                ParagraphRequest pr = request.getParagraphs().get(i);
                int sortOrder = pr.getSortOrder() != null ? pr.getSortOrder() : i;

                if (pr.getId() != null) {
                    entity.getParagraphs().stream()
                            .filter(p -> p.getId().equals(pr.getId()))
                            .findFirst()
                            .ifPresent(existing -> {
                                existing.setContent(pr.getContent());
                                existing.setSortOrder(sortOrder);
                            });
                } else {
                    AboutThinkingParagraph newParagraph = AboutThinkingParagraph.builder()
                            .content(pr.getContent())
                            .sortOrder(sortOrder)
                            .build();
                    entity.addParagraph(newParagraph);
                }
            }
        }

        AboutThinkingBig saved = thinkingBigRepository.save(entity);
        return mapToCmsResponse(saved);
    }

    @Transactional
    public AboutThinkingBigCmsResponse addParagraph(ParagraphRequest request) {
        AboutThinkingBig entity = thinkingBigRepository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new RuntimeException("Thinking Big data not found"));

        int nextOrder = entity.getParagraphs().stream()
                .mapToInt(AboutThinkingParagraph::getSortOrder)
                .max()
                .orElse(-1) + 1;

        AboutThinkingParagraph paragraph = AboutThinkingParagraph.builder()
                .content(request.getContent() != null ? request.getContent() : "")
                .sortOrder(request.getSortOrder() != null ? request.getSortOrder() : nextOrder)
                .build();

        entity.addParagraph(paragraph);
        AboutThinkingBig saved = thinkingBigRepository.save(entity);
        return mapToCmsResponse(saved);
    }

    @Transactional
    public AboutThinkingBigCmsResponse updateParagraph(Long paragraphId, ParagraphRequest request) {
        AboutThinkingParagraph paragraph = paragraphRepository.findById(paragraphId)
                .orElseThrow(() -> new RuntimeException("Paragraph not found with id: " + paragraphId));

        if (request.getContent() != null) paragraph.setContent(request.getContent());
        if (request.getSortOrder() != null) paragraph.setSortOrder(request.getSortOrder());

        paragraphRepository.save(paragraph);

        AboutThinkingBig entity = thinkingBigRepository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new RuntimeException("Thinking Big data not found"));
        return mapToCmsResponse(entity);
    }

    @Transactional
    public AboutThinkingBigCmsResponse deleteParagraph(Long paragraphId) {
        AboutThinkingBig entity = thinkingBigRepository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new RuntimeException("Thinking Big data not found"));

        AboutThinkingParagraph toRemove = entity.getParagraphs().stream()
                .filter(p -> p.getId().equals(paragraphId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Paragraph not found with id: " + paragraphId));

        entity.removeParagraph(toRemove);
        AboutThinkingBig saved = thinkingBigRepository.save(entity);

        List<AboutThinkingParagraph> remaining = saved.getParagraphs();
        for (int i = 0; i < remaining.size(); i++) {
            remaining.get(i).setSortOrder(i);
        }
        thinkingBigRepository.save(saved);

        return mapToCmsResponse(saved);
    }

    // ==================== HELPERS ====================

    private String stripHtml(String html) {
        if (html == null || html.isBlank()) return "";
        return html
                .replaceAll("<[^>]*>", "")
                .replace("&nbsp;", " ")
                .replace("&amp;", "&")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&quot;", "\"")
                .replace("&#39;", "'")
                .replaceAll("\\s+", " ")
                .trim();
    }

    // For live website - plain string paragraphs
    private AboutThinkingBigResponse mapToPublicResponse(AboutThinkingBig entity) {
        List<String> paragraphStrings = entity.getParagraphs().stream()
                .sorted(Comparator.comparingInt(AboutThinkingParagraph::getSortOrder))
                .map(p -> stripHtml(p.getContent()))
                .filter(text -> !text.isBlank())
                .collect(Collectors.toList());

        return AboutThinkingBigResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .paragraphs(paragraphStrings)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    // For CMS - full paragraph objects
    private AboutThinkingBigCmsResponse mapToCmsResponse(AboutThinkingBig entity) {
        List<ParagraphResponse> paragraphResponses = entity.getParagraphs().stream()
                .sorted(Comparator.comparingInt(AboutThinkingParagraph::getSortOrder))
                .map(p -> ParagraphResponse.builder()
                        .id(p.getId())
                        .content(p.getContent())
                        .sortOrder(p.getSortOrder())
                        .createdAt(p.getCreatedAt())
                        .updatedAt(p.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());

        return AboutThinkingBigCmsResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .paragraphs(paragraphResponses)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}