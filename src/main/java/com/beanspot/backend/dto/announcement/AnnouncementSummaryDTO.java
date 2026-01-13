package com.beanspot.backend.dto.announcement;

import com.beanspot.backend.entity.announcement.Announcement;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class AnnouncementSummaryDTO {
    private Long id;
    private String title;
    private String organizer;
    private String region;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private String thumbnailUrl;
    private int viewCount;

    public static AnnouncementSummaryDTO from(Announcement announcement) {
        return AnnouncementSummaryDTO.builder()
                .id(announcement.getId())
                .title(announcement.getTitle())
                .organizer(announcement.getOrganizer())
                .region(announcement.getLocation())
                .type(announcement.getType())
                .startDate(announcement.getStartDate())
                .endDate(announcement.getEndDate())
                .viewCount(announcement.getViewCount())
                .build();
    }
}
