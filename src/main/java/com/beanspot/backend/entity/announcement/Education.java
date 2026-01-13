package com.beanspot.backend.entity.announcement;

import com.beanspot.backend.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "announcement_education")
public class Education{
    @Id
    @Column(name = "announcement_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;

    private Integer fee;

    @Column(name = "curriculum_url")
    private String curriculumUrl;
}
