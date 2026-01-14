package com.beanspot.backend.dto.announcement;

import com.beanspot.backend.entity.announcement.Supporter;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class SupporterDTO {

    @Builder
    @Getter
    public static class Detail{
        private String benefits;
        private String selectionProcess;

        public static Detail from(Supporter supporter) {
            if (supporter == null) return null;

            return Detail.builder()
                    .benefits(supporter.getBenefits())
                    .selectionProcess(supporter.getSelectionProcess())
                    .build();
        }
    }

    @Getter
    public static class Create{
        @NotBlank
        private String benefits;

        @NotBlank
        private String selectionProcess;
    }

    @Getter
    public static class Update {
        private String benefits;
        private String selectionProcess;
    }
}
