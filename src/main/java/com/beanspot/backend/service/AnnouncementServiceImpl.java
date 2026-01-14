package com.beanspot.backend.service;

import com.beanspot.backend.common.response.PageResponse;
import com.beanspot.backend.dto.announcement.*;
import com.beanspot.backend.entity.announcement.*;
import com.beanspot.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public AnnouncementDTO.Detail getAnnouncementDetail(Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No announcement found with id: " + id));

        AnnouncementDTO.Detail dto = AnnouncementDTO.Detail.from(announcement);

        // 유형별 상세 정보 주입
        switch (announcement.getType()) {
            case "VOLUNTEER" -> {
                Volunteer v = volunteerRepository.findById(id).orElse(null);
                dto.setVolunteer(VolunteerDTO.Detail.from(v));
            }
            case "SUPPORTER" -> {
                Supporter s = supporterRepository.findById(id).orElse(null);
                dto.setSupporter(SupporterDTO.Detail.from(s));
            }
            case "EDUCATION" -> {
                Education e = educationRepository.findById(id).orElse(null);
                dto.setEducation(EducationDTO.Detail.from(e));
            }
            case "CHALLENGE" -> {
                Challenge c = challengeRepository.findById(id).orElse(null);
                dto.setChallenge(ChallengeDTO.Detail.from(c));
            }
        }

        return dto;
    }

    @Override
    @Transactional
    public void increaseViewCount(Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No announcement found with id: " + id));

        announcement.setViewCount(announcement.getViewCount() + 1);
    }

    @Override
    @Transactional
    public void addAnnouncement(AnnouncementDTO.Create reqDTO) {
        // 공통 공고 저장
        Announcement announcement = Announcement.builder()
                .title(reqDTO.getTitle())
                .content(reqDTO.getContent())
                .type(reqDTO.getType())
                .startDate(reqDTO.getStartDate())
                .endDate(reqDTO.getEndDate())
                .build();

        announcementRepository.save(announcement);

        // 유형별 공고 저장
        switch (reqDTO.getType()) {
            case "VOLUNTEER" -> saveVolunteer(reqDTO.getVolunteer(), announcement);
            case "SUPPORTER" -> saveSupporter(reqDTO.getSupporter(), announcement);
            case "EDUCATION" -> saveEducation(reqDTO.getEducation(), announcement);
            case "CHALLENGE" -> saveChallenge(reqDTO.getChallenge(), announcement);
            default -> throw new RuntimeException("지원하지 않는 타입입니다. " + reqDTO.getType());
        }
    }

    @Override
    @Transactional
    public void updateAnnouncement(Long id, AnnouncementDTO.Update reqDTO) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("공고가 존재하지 않습니다."));

        announcement.setTitle(reqDTO.getTitle());
        announcement.setStartDate(reqDTO.getStartDate());
        announcement.setEndDate(reqDTO.getEndDate());
    }

    @Override
    @Transactional
    public void deleteAnnouncement(Long id) {
        if (!announcementRepository.existsById(id)) {
            throw new IllegalArgumentException("공고가 존재하지 않습니다.");
        }
        announcementRepository.deleteById(id);
    }

    private void saveVolunteer(VolunteerDTO.Create dto, Announcement announcement) {
        if(dto == null) throw new IllegalArgumentException("Volunteer 정보가 필요합니다.");

        Volunteer v = Volunteer.builder()
                .announcement(announcement)
                .fee(dto.getFee())
                .isServiceHoursVerified(dto.getIsServiceHoursVerified())
                .build();

        volunteerRepository.save(v);
    }

    private void saveSupporter(SupporterDTO.Create dto, Announcement announcement) {
        if(dto == null) throw new IllegalArgumentException("Supporter 정보가 필요합니다.");

        Supporter supporter = Supporter.builder()
                .announcement(announcement)
                .selectionProcess(dto.getSelectionProcess())
                .benefits(dto.getBenefits())
                .build();

        supporterRepository.save(supporter);
    }

    private void saveEducation(EducationDTO.Create dto, Announcement announcement) {
        if(dto == null) throw new IllegalArgumentException("Education 정보가 필요합니다.");

        Education education = Education.builder()
                .announcement(announcement)
                .curriculumUrl(dto.getCurriculumUrl())
                .fee(dto.getFee())
                .build();

        educationRepository.save(education);
    }

    private void saveChallenge(ChallengeDTO.Create dto, Announcement announcement) {
        if(dto == null) throw new IllegalArgumentException("Challenge 정보가 필요합니다.");

        Challenge challenge = Challenge.builder()
                .announcement(announcement)
                .teamSize(dto.getTeamSize())
                .participantTarget(dto.getParticipantTarget())
                .prizeScale(dto.getPrizeScale())
                .build();

        challengeRepository.save(challenge);
    }
}
