package com.beanspot.backend.controller;

import com.beanspot.backend.common.response.ApiResponse;
import com.beanspot.backend.common.response.PageResponse;
import com.beanspot.backend.dto.announcement.AnnouncementDetailDTO;
import com.beanspot.backend.dto.announcement.AnnouncementSummaryDTO;
import com.beanspot.backend.service.AnnouncementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name="공고 관련 API", description = "공고 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/announcement")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @GetMapping("")
    public PageResponse<AnnouncementSummaryDTO> getAnnouncements(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String region,
            @RequestParam(required = false, defaultValue = "latest") String sort,
            @RequestParam(required = false) Integer limit,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return announcementService.getAnnouncements(
                keyword, type, category, region,
                sort, page, size, limit
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<AnnouncementDetailDTO> getAnnouncement(@PathVariable Long id) {
        return ApiResponse.ok(announcementService.getAnnouncementDetail(id));
    }
}
