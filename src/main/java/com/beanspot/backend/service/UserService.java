package com.beanspot.backend.service;

public interface UserService {
    SignUpUserDTO.Res createUser(final SignUpUserDTO.Req userDTO);
    User getByCredential(final String email, final String password, PasswordEncoder encoder);
    boolean isNicknameAvailable(final String nickname);
    User getUserProfileById(final Long id);
}
