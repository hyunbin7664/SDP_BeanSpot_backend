package com.beanspot.backend.service;

import com.beanspot.backend.common.exception.CustomException;
import com.beanspot.backend.common.exception.ErrorCode;
import com.beanspot.backend.dto.auth.SignUpUserDTO;
import com.beanspot.backend.entity.User;
import com.beanspot.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public SignUpUserDTO.Res createUser(final SignUpUserDTO.Req userDTO) {

        final String email = userDTO.getEmail();
        final String nickname = userDTO.getNickname();

        if(userRepository.existsByEmail(email)){
            log.warn("User with email {} already exists", email);
            throw new CustomException(ErrorCode.AUTH_USERID_ALREADY_EXISTS);
        }

        if(!isNicknameAvailable(nickname)){
            log.warn("User with nickname {} already exists", nickname);
            throw new CustomException(ErrorCode.AUTH_NICKNAME_ALREADY_EXISTS);
        }

        User user = User.builder()
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .nickname(userDTO.getNickname())
                .name(userDTO.getName())
                .socialType(com.beanspot.backend.entity.SocialType.NONE)
                .build();

        userRepository.save(user);

        return  SignUpUserDTO.Res.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();

    }

    public User getByCredential(final String email, final String password, PasswordEncoder encoder) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new CustomException(ErrorCode.AUTH_EMAIL_NOT_FOUND));
        if(!encoder.matches(password, user.getPassword())){
            throw new CustomException(ErrorCode.AUTH_PASSWORD_MISMATCH);
        }
        return user;
    }

    public boolean isNicknameAvailable(final String nickname) {
        return !userRepository.existsByNickname(nickname);
    }

    @Override
    public User getUserProfileById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new CustomException(ErrorCode.AUTH_LOGIN_REQUIRED));
    }
}
