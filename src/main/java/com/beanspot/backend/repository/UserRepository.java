package com.beanspot.backend.repository;

import com.beanspot.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@RequestMapping
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByNickname(String nickname);
    Boolean existsByEmail(String email);
    User findByEmailAndPassword(String email, String password);
}
