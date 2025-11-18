package com.beanspot.backend.controller;

import com.beanspot.backend.common.response.ApiResponse;
import com.beanspot.backend.dto.auth.LoginUserDTO;
import com.beanspot.backend.dto.auth.SignUpUserDTO;
import com.beanspot.backend.entity.User;
import com.beanspot.backend.security.TokenProvider;
import com.beanspot.backend.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name="인증 API", description = "회원가입 및 로그인 관련 API입니다.")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;


    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ApiResponse<?> signup(@RequestBody SignUpUserDTO.Req userDTO) {

        if (userDTO == null || userDTO.getPassword() == null) {
            throw new RuntimeException("Invalid password value.");
        }
        SignUpUserDTO.Res responseUserDTO = userService.createUser(userDTO);
        return ApiResponse.ok(responseUserDTO);

    }


    @PostMapping("/login")
    public ApiResponse<?>  login(@RequestBody LoginUserDTO.Req userDTO) {

        User user = userService.getByCredential(
                userDTO.getUserId(),
                userDTO.getPassword(),
                passwordEncoder
        );

        // TODO 이메일 인증 체크

        final String accessToken = tokenProvider.createAccessToken(user);
        final String refreshToken = tokenProvider.createRefreshToken(user);



        final LoginUserDTO.Res reponseUserDTO = LoginUserDTO.Res.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .nickname(user.getNickname())
                .id(user.getId())
                .build();

        return ApiResponse.ok(reponseUserDTO);

    }

}
