package com.beanspot.backend.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SignUpSocialUserDTO {

    @Getter
    public static class Req {
        @NotBlank(message = "닉네임 작성은 필수입니다.")
        private String nickname;
        private String userName;
        private String phone;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Res {
        private String socialId;
        private String nickname;
    }
}
