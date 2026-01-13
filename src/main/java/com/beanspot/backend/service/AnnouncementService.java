package com.beanspot.backend.service;

import com.beanspot.backend.common.response.PageResponse;
import com.beanspot.backend.dto.announcement.AnnouncementDetailDTO;
import com.beanspot.backend.dto.announcement.AnnouncementSummaryDTO;

import java.util.List;

public interface AnnouncementService {
    public PageResponse<AnnouncementSummaryDTO> getAnnouncements(
            String keyword,
            String type,
            String category,
            String region,
            String sort,
            int page,
            int size,
            Integer limit
    );

    public AnnouncementDetailDTO getAnnouncementDetail(Long id);
}
