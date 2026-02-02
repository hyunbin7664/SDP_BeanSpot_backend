package com.beanspot.backend.dto.user;


import com.beanspot.backend.entity.SocialType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserProfileDTO {
    private Long id;
    private String userId;
    private String nickname;
    private String name;
    private String phone;
    private String profileUrl;
    private String address;
    private String socialId;
    private SocialType socialType;
}
