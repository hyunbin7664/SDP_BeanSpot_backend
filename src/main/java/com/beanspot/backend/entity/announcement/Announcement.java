package com.beanspot.backend.entity.announcement;
import com.beanspot.backend.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "announcement_common")
public class Announcement extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String organizer;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "link_url")
    private String linkUrl;

    @Column(name = "view_count")
    private int viewCount;

    private String location;

    private String closingment;

    @Column(name = "recruitment_start")
    private LocalDate recruitmentStart;

    @Column(name = "recruitment_end")
    private LocalDate recruitmentEnd;

    // 공고 유형 (VOLUNTEER, SUPPORTER, EDUCATION, CHALLENGE)
    private String type;
}
