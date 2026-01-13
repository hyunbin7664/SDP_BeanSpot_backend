package com.beanspot.backend.dto.announcement;

import com.beanspot.backend.entity.announcement.Volunteer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerDetailDTO {
    private Boolean isServiceHoursVerified;
    private Integer fee;

    public static VolunteerDetailDTO from(Volunteer v) {
        if (v == null) return null;
        return VolunteerDetailDTO.builder()
                .isServiceHoursVerified(v.getIsServiceHoursVerified())
                .fee(v.getFee())
                .build();
    }
}
