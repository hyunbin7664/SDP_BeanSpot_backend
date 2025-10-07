package com.beanspot.backend.service;

import com.beanspot.backend.dto.auth.SignUpUserDTO;
import com.beanspot.backend.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserService {
    SignUpUserDTO.Res createUser(final SignUpUserDTO.Req userDTO);
    User getByCredential(final String email, final String password, PasswordEncoder encoder);
    boolean isNicknameAvailable(final String nickname);
    User getUserProfileById(final Long id);
}
