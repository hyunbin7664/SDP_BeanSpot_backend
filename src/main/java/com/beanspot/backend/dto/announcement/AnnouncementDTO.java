package com.beanspot.backend.dto.announcement;

import com.beanspot.backend.entity.announcement.Announcement;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class AnnouncementDTO {

    @Builder
    @Getter
    @Setter
    public static class Detail{
        private Long id;
        private String title;
        private String content;
        private String organizer;
        private String type;
        private String imgUrl;
        private String location;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate startDate;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate endDate;

        // 유형별 상세 정보
        private VolunteerDTO.Detail volunteer;
        private SupporterDTO.Detail supporter;
        private EducationDTO.Detail education;
        private ChallengeDTO.Detail challenge;

        public static Detail from(Announcement announcement) {
            return Detail.builder()
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

    @Getter
    public static class Create{
        @NotBlank
        private String title;
        @NotBlank
        private String content;
        @NotBlank
        private String type; // CHALLENGE / EDUCATION / SUPPORTER / VOLUNTEER

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate startDate;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate endDate;

        private VolunteerDTO.Create volunteer;
        private ChallengeDTO.Create challenge;
        private EducationDTO.Create education;
        private SupporterDTO.Create supporter;
    }

    @Getter
    public static class Update{
        private String title;
        private String content;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate startDate;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate endDate;

        private VolunteerDTO.Update volunteer;
        private SupporterDTO.Update supporter;
        private EducationDTO.Update education;
        private ChallengeDTO.Update challenge;
    }
}
