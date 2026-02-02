package com.beanspot.backend.service;

import com.beanspot.backend.oauth.KakaoInfo;

public interface KakaoLoginService {
    KakaoInfo kakaoLogin(final String code, final String redirectUrl);
}
