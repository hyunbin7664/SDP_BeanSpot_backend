package com.beanspot.backend.dto.announcement;

import com.beanspot.backend.entity.announcement.Challenge;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class ChallengeDTO {

    @Builder
    @Getter
    public static class Detail{
        private String prizeScale;
        private String participantTarget;
        private String teamSize;

        public static Detail from(Challenge challenge) {
            if (challenge == null) return null;
            return Detail.builder()
                    .prizeScale(challenge.getPrizeScale())
                    .participantTarget(challenge.getParticipantTarget())
                    .teamSize(challenge.getTeamSize())
                    .build();
        }
    }

    @Getter
    public static class Create {
        @NotBlank
        private String prizeScale;

        @NotBlank
        private String participantTarget;

        @NotBlank
        private String teamSize;
    }

    @Getter
    public static class Update {

        private String prizeScale;
        private String participantTarget;
        private String teamSize;
    }

}
