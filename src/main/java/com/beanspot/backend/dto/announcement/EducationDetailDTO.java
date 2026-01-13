package com.beanspot.backend.dto.announcement;

import com.beanspot.backend.entity.announcement.Education;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationDetailDTO {

    private Integer fee;
    private String curriculumUrl;

    public static EducationDetailDTO from(Education education) {
        if (education == null) return null;

        return EducationDetailDTO.builder()
                .fee(education.getFee())
                .curriculumUrl(education.getCurriculumUrl())
                .build();
    }
}
