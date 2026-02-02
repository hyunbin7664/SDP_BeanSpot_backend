package com.beanspot.backend.dto.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CheckNicknameResponseDTO {
    private final boolean available; // 닉네임 사용 가능 여부
    private final String nickname;   // 확인한 닉네임
    private final String message;
}
