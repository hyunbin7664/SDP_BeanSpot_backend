package com.beanspot.backend.dto.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserSummaryDTO {
    private Long userId;
    private String nickname;
}
