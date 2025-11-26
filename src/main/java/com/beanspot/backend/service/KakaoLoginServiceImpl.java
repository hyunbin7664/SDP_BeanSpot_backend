package com.beanspot.backend.service;

import com.beanspot.backend.oauth.KakaoInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoLoginServiceImpl implements KakaoLoginService {

    private static final Logger log = LoggerFactory.getLogger(KakaoLoginServiceImpl.class);
    @Value("${oauth.kakao.client-id}")
    private String clientId;

    public KakaoInfo kakaoLogin(String code, String redirectUrl){
        String accessToken = getAccessToken(code, redirectUrl);
        return getKakaoUserInfo(accessToken);
    }

    private String getAccessToken(String code, String redirectUrl){
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8 ");

        log.info("clientId: " + clientId);
        log.info("redirectUrl: " + redirectUrl);

        // BODY 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUrl);
        body.add("code", code);

        //요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        //HTTP 응답 -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = null;
        try {
            json = mapper.readTree(responseBody);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

        return json.get("access_token").asText();

    }

    private KakaoInfo getKakaoUserInfo(String accessToken){
        HashMap<String, Object> userInfo = new HashMap<String, Object>();

        //HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        KakaoInfo kakaoInfo = null;

        try{
            Map<String, Object> attributes = mapper.readValue(responseBody, Map.class);
            kakaoInfo = new KakaoInfo(attributes);

        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return kakaoInfo;
    }
}
