package com.beanspot.backend.repository;

import com.beanspot.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@RequestMapping
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    Boolean existsByNickname(String nickname);
    Boolean existsByUserId(String userId);
    User findByUserIdAndPassword(String userId, String password);

    Optional<User> findBySocialId(String socialId);
    Boolean existsBySocialId(String socialId);
}
