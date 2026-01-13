package com.beanspot.backend.service;

import com.beanspot.backend.common.response.PageResponse;
import com.beanspot.backend.dto.announcement.*;
import com.beanspot.backend.entity.announcement.*;
import com.beanspot.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final VolunteerRepository volunteerRepository;
    private final SupporterRepository supporterRepository;
    private final EducationRepository educationRepository;
    private final ChallengeRepository challengeRepository;

    @Override
    public PageResponse<AnnouncementSummaryDTO> getAnnouncements(String keyword, String type, String category, String region, String sort, int page, int size, Integer limit) {

        if( limit != null ){ // 페이징 없는 홈 화면용

            List<Announcement> list = announcementRepository.findTopByFilters(
                    keyword,
                    type,
                    region,
                    sort,
                    limit
            );

            List<AnnouncementSummaryDTO> items = list.stream()
                    .map(AnnouncementSummaryDTO::from)
                    .toList();

            return PageResponse.of(items);
        }

        // 일반 리스트 조회
        Page<Announcement> pageResult = announcementRepository.searchWithFilters(
                keyword,
                type,
                region,
                sort,
                PageRequest.of(page, size)
        );

        List<AnnouncementSummaryDTO> items = pageResult.getContent()
                .stream()
                .map(AnnouncementSummaryDTO::from)
                .toList();

        return PageResponse.of(pageResult, items);
    }

    @Override
    public AnnouncementDetailDTO getAnnouncementDetail(Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No announcement found with id: " + id));

        //조회수증가
        announcement.setViewCount(announcement.getViewCount() + 1);

        AnnouncementDetailDTO dto = AnnouncementDetailDTO.from(announcement);

        // 유형별 상세 정보 주입
        switch (announcement.getType()) {
            case "VOLUNTEER" -> {
                Volunteer v = volunteerRepository.findById(id).orElse(null);
                dto.setVolunteerDetail(VolunteerDetailDTO.from(v));
            }
            case "SUPPORTER" -> {
                Supporter s = supporterRepository.findById(id).orElse(null);
                dto.setSupporterDetail(SupporterDetailDTO.from(s));
            }
            case "EDUCATION" -> {
                Education e = educationRepository.findById(id).orElse(null);
                dto.setEducationDetail(EducationDetailDTO.from(e));
            }
            case "CHALLENGE" -> {
                Challenge c = challengeRepository.findById(id).orElse(null);
                dto.setChallengeDetail(ChallengeDetailDTO.from(c));
            }
        }

        return dto;
    }
}
