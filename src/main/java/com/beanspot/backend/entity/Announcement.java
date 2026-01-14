package com.beanspot.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "announcement_common") // ERD 명칭 반영
public class Announcement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcement_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private User admin; // 등록한 관리자

    private String title;          // 공고 제목

    @Column(columnDefinition = "TEXT")
    private String content;        // 상세 내용 (피그마 2단계)

    private String organizer;      // 운영 주체 (기존 host 대신 피그마 명칭 반영)
    private String recruitmentCount; // 모집 인원
    private String location;       // 활동 지역

    private LocalDateTime recruitmentStart; // 모집 시작일
    private LocalDateTime recruitmentEnd;   // 모집 종료일

    private LocalDateTime startDate; // 활동 시작일
    private LocalDateTime endDate;   // 활동 종료일

    private String type;           // 공고 유형 (대외활동, 동아리 등)
    private String imgUrl;         // 포스터 이미지 URL
    private String linkUrl;         // 신청 링크 URL

    private String closingment;    // 마무리 멘트 (피그마 2단계 하단)

    @Builder.Default
    private Integer viewCount = 0; // 조회수 (초기값 0)

    // ⭐ 세부 일정과의 1:N 관계 설정 (피그마 3단계)
    @Builder.Default
    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnnouncementSchedule> schedules = new ArrayList<>();

    // == 연관관계 편의 메서드 == //
    public void addSchedule(AnnouncementSchedule schedule) {
        this.schedules.add(schedule);
        schedule.setAnnouncement(this);
    }
}