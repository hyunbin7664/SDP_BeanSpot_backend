package com.beanspot.backend.dto.announcement;

import com.beanspot.backend.entity.announcement.Challenge;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeDetailDTO {
    private String prizeScale;
    private String participantTarget;
    private String teamSize;

    public static ChallengeDetailDTO from(Challenge challenge) {
        if (challenge == null) return null;
        return ChallengeDetailDTO.builder()
                .prizeScale(challenge.getPrizeScale())
                .participantTarget(challenge.getParticipantTarget())
                .teamSize(challenge.getTeamSize())
                .build();
    }
}
