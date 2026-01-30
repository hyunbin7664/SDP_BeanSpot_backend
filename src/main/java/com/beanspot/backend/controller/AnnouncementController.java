package com.beanspot.backend.controller;

import com.beanspot.backend.common.response.ApiResponse;
import com.beanspot.backend.common.response.PageResponse;
import com.beanspot.backend.dto.announcement.AnnouncementDTO;
import com.beanspot.backend.dto.announcement.AnnouncementSummaryDTO;
import com.beanspot.backend.dto.announcement.AnnouncementSearchConditionDTO;
import com.beanspot.backend.entity.announcement.AnnouncementType;
import com.beanspot.backend.entity.search.SortType;
import com.beanspot.backend.listener.SearchExcutedEvent;
import com.beanspot.backend.security.CurrentUserId;
import com.beanspot.backend.service.search.RecentSearchService;
import com.beanspot.backend.service.announcement.AnnouncementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.Normalizer;
import java.time.YearMonth;
import java.util.List;

@Slf4j
@Tag(name="공고 관련 API", description = "공고 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/announcement")
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final RecentSearchService recentSearchService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @GetMapping("")
    public PageResponse<AnnouncementSummaryDTO> getAnnouncements(
            @CurrentUserId Long userId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) AnnouncementType type,
            @RequestParam(required = false) List<String> hashtags,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM") YearMonth recruitmentMonth,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM") YearMonth activityMonth,
            @RequestParam(required = false) Double minLat,
            @RequestParam(required = false) Double maxLat,
            @RequestParam(required = false) Double minLng,
            @RequestParam(required = false) Double maxLng,
            @RequestParam(required = false, defaultValue = "LATEST") SortType sort,
            @RequestParam(required = false) Integer limit,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        AnnouncementSearchConditionDTO condition =  AnnouncementSearchConditionDTO.builder()
                .keyword(keyword)
                .type(type)
                .hashtags(hashtags)
                .region(region)
                .recruitmentMonth(recruitmentMonth)
                .activityMonth(activityMonth)
                .minLat(minLat)
                .maxLat(maxLat)
                .minLng(minLng)
                .maxLng(maxLng)
                .sort(sort)
                .limit(limit)
                .page(page)
                .size(size)
                .build();
        //최근 검색어 저장
        String normalizedKeyword = keyword == null ? null : keyword.trim();
        if(userId != null && StringUtils.hasText(normalizedKeyword)) {
            recentSearchService.saveRecentSearch(userId, keyword);
            applicationEventPublisher.publishEvent(
                    new SearchExcutedEvent(userId,keyword)
            );
        }

        return announcementService.getAnnouncements(condition);
    }

    @GetMapping("/{id}")
    public ApiResponse<AnnouncementDTO.Detail> getAnnouncementDetail(@PathVariable Long id) {
        announcementService.increaseViewCount(id);
        return ApiResponse.ok(announcementService.getAnnouncementDetail(id));
    }
}
