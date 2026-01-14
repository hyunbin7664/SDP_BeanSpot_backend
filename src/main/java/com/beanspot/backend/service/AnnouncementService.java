package com.beanspot.backend.service;

import com.beanspot.backend.common.response.PageResponse;
import com.beanspot.backend.dto.announcement.AnnouncementDTO;
import com.beanspot.backend.dto.announcement.AnnouncementSummaryDTO;


public interface AnnouncementService {
    PageResponse<AnnouncementSummaryDTO> getAnnouncements(
            String keyword,
            String type,
            String category,
            String region,
            String sort,
            int page,
            int size,
            Integer limit
    );

    AnnouncementDTO.Detail getAnnouncementDetail(Long id);

    void increaseViewCount(Long id);

    void addAnnouncement(AnnouncementDTO.Create announcement);

    void updateAnnouncement(Long id, AnnouncementDTO.Update announcement);

    void deleteAnnouncement(Long id);
}
