package com.beanspot.backend.service;

import com.beanspot.backend.dto.admin.AnnouncementRequestDto;
import com.beanspot.backend.entity.Announcement;
import com.beanspot.backend.entity.AnnouncementSchedule;
import com.beanspot.backend.entity.User;
import com.beanspot.backend.repository.AnnouncementRepository;
import com.beanspot.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createAnnouncement(Long adminId, AnnouncementRequestDto dto) {
        // 1. 관리자 존재 여부 확인
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("관리자를 찾을 수 없습니다."));

        // 2. Announcement 엔티티 생성 (피그마 1, 2단계 데이터)
        Announcement announcement = Announcement.builder()
                .title(dto.getTitle())
                .organizer(dto.getOrganizer())      // 운영 주체
                .recruitmentCount(dto.getRecruitmentCount())
                .location(dto.getLocation())
                .recruitmentStart(LocalDateTime.parse(dto.getRecruitmentStart()))
                .recruitmentEnd(LocalDateTime.parse(dto.getRecruitmentEnd())) // 글자를 날짜 형식으로 변환해서 넣음                .startDate(dto.getStartDate())
                .endDate(LocalDateTime.parse(dto.getEndDate()))                .type(dto.getType())
                .imgUrl(dto.getImgUrl())
                .linkUrl(dto.getLinkUrl())
                .content(dto.getContent())          // 상세 내용
                .closingment(dto.getClosingment())  // 마무리 멘트
                .viewCount(0)                       // 초기 조회수
                .build();

        // 3. 세부 일정 처리 (피그마 3단계 데이터 - 1:N 관계)
        if (dto.getSchedules() != null && !dto.getSchedules().isEmpty()) {
            dto.getSchedules().forEach(scheduleDto -> {
                AnnouncementSchedule schedule = AnnouncementSchedule.builder()
                        .dateInfo(scheduleDto.getDateInfo())
                        .description(scheduleDto.getDescription())
                        .build();

                // 연관 관계 편의 메서드를 통해 Announcement와 연결
                announcement.addSchedule(schedule);
            });
        }

        // 4. 저장 (Cascade 설정 덕분에 일정까지 한 번에 저장됩니다)
        return announcementRepository.save(announcement).getId();
    }
}