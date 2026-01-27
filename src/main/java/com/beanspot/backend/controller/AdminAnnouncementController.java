package com.beanspot.backend.controller;

import com.beanspot.backend.common.response.ApiResponse;
import com.beanspot.backend.dto.announcement.AnnouncementDTO;
import com.beanspot.backend.service.AnnouncementIndexService;
import com.beanspot.backend.service.announcement.AnnouncementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Tag(name="관리자용 공고 관련 API", description = "관리자용 공고 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/announcement")
public class AdminAnnouncementController {

    private final AnnouncementService announcementService;
    private final AnnouncementIndexService indexService;

    @PostMapping()
    public ApiResponse<?> createAnnouncement(@RequestBody AnnouncementDTO.Create request) {
        announcementService.addAnnouncement(request);
        return ApiResponse.ok("공고 등록");
    }

    @PutMapping("/{id}")
    public ApiResponse<?> update(@PathVariable Long id, @RequestBody AnnouncementDTO.Update request) {
        announcementService.updateAnnouncement(id, request);
        return ApiResponse.ok("공고 업데이트");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable Long id){
        announcementService.deleteAnnouncement(id);
        return ApiResponse.ok("공고 삭제");
    }



    // 전체 공고 ES 재색인
    @PostMapping("/es/reindex")
    public Map<String, Object> reindexAll() {
        int count = indexService.reindexAll();

        return Map.of("message", "ES reindex completed","indexedCount", count);
    }


}
