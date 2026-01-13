package com.beanspot.backend.dto.announcement;

import com.beanspot.backend.entity.announcement.Announcement;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Builder
@Setter
public class AnnouncementDetailDTO {

    private Long id;
    private String title;
    private String content;
    private String organizer;
    private String type;

    private String imgUrl;
    private String location;

    private LocalDate startDate;
    private LocalDate endDate;

    // 유형별 상세 정보
    private VolunteerDetailDTO volunteerDetail;
    private SupporterDetailDTO supporterDetail;
    private EducationDetailDTO educationDetail;
    private ChallengeDetailDTO challengeDetail;

    public static AnnouncementDetailDTO from(Announcement announcement) {
        return AnnouncementDetailDTO.builder()
                .id(announcement.getId())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .organizer(announcement.getOrganizer())
                .type(announcement.getType())
                .imgUrl(announcement.getImgUrl())
                .location(announcement.getLocation())
                .startDate(announcement.getStartDate())
                .endDate(announcement.getEndDate())
                .build();
    }
}
