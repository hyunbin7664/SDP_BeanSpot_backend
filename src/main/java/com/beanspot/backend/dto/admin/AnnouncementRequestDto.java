package com.beanspot.backend.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementRequestDto {

    // 기본 사항 (피그마 1단계 & ERD 매칭)
    private String title;              // 제목 (ERD: title)
    private String organizer;          // 운영 주체 (ERD: organizer)
    private String recruitmentCount;   // 모집 인원 (ERD: recruitment_count)
    private String location;           // 활동 지역 (ERD: location)
    private String recruitmentStart;   // 모집 시작일 (ERD: recruitment_start)
    private String recruitmentEnd;     // 모집 종료일 (ERD: recruitment_end)
    private String startDate;          // 활동 시작일 (ERD: start_date)
    private String endDate;            // 활동 종료일 (ERD: end_date)
    private String type;               // 공고 유형 (ERD: type - 예: 대외활동, 동아리)
    private String imgUrl;             // 포스터 이미지 (ERD: img_url)
    private String linkUrl;            // 신청 링크 (ERD: link_url)

    // 상세 내용 및 마감 (피그마 2단계 & ERD 매칭)
    private String content;            // 상세 내용 (ERD: content)
    private String closingment;        // 마무리 멘트 (ERD: closingment)

    // 세부 일정 (피그마 3단계: 리스트 형태)
    private List<ScheduleDto> schedules;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScheduleDto {
        private String dateInfo;       // 날짜 (예: "01.11")
        private String description;    // 일정 내용 (예: "대면 발대식 진행")
    }
}