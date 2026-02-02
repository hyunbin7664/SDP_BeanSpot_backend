package com.beanspot.backend.entity;

public enum SocialType {
    KAKAO,
    NAVER,
    GOOGLE,
    NONE;

    public static SocialType from(String input){
        try{
            return SocialType.valueOf(input.toUpperCase());
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid social type: " + input);
        }
    }
}
