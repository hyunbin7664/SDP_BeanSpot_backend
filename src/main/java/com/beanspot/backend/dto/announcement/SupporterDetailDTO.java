package com.beanspot.backend.dto.announcement;

import com.beanspot.backend.entity.announcement.Supporter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupporterDetailDTO {
    private String benefits;
    private String selectionProcess;

    public static SupporterDetailDTO from(Supporter supporter) {
        if (supporter == null) return null;

        return SupporterDetailDTO.builder()
                .benefits(supporter.getBenefits())
                .selectionProcess(supporter.getSelectionProcess())
                .build();
    }
}
