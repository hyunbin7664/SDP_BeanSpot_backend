package com.beanspot.backend.repository;

import com.beanspot.backend.entity.announcement.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
}
