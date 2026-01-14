package com.beanspot.backend.dto.announcement;

import com.beanspot.backend.entity.announcement.Volunteer;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class VolunteerDTO {

    @Builder
    @Getter
    public static class Detail{
        private Boolean isServiceHoursVerified;
        private Integer fee;

        public static Detail from(Volunteer v) {
            if (v == null) return null;
            return Detail.builder()
                    .isServiceHoursVerified(v.getIsServiceHoursVerified())
                    .fee(v.getFee())
                    .build();
        }
    }

    @Getter
    public static class Create{
        @NotBlank
        private Boolean isServiceHoursVerified;

        @NotBlank
        private Integer fee;

    }
    @Getter
    public static class Update {
        private Boolean isServiceHoursVerified;
        private Integer fee;
    }
}
