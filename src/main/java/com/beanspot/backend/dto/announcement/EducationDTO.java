package com.beanspot.backend.dto.announcement;

import com.beanspot.backend.entity.announcement.Education;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class EducationDTO {

    @Builder
    @Getter
    public static class Detail{
        private Integer fee;
        private String curriculumUrl;

        public static Detail from(Education education) {
            if (education == null) return null;

            return Detail.builder()
                    .fee(education.getFee())
                    .curriculumUrl(education.getCurriculumUrl())
                    .build();
        }
    }

    @Getter
    public static class Create{
        @NotBlank
        private Integer fee;

        @NotBlank
        private String curriculumUrl;
    }


    @Getter
    public static class Update {
        private Integer fee;
        private String curriculumUrl;
    }

}
