package com.beanspot.backend.entity.announcement;

import com.beanspot.backend.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "announcement_challenge")
public class Challenge extends BaseEntity {
    @Id
    @Column(name = "announcement_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;

    @Column(name = "prize_scale")
    private String prizeScale;

    @Column(name = "participant_target")
    private String participantTarget;

    @Column(name = "team_size")
    private String teamSize;
}
