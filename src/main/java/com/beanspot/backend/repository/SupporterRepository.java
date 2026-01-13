package com.beanspot.backend.repository;

import com.beanspot.backend.entity.announcement.Supporter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupporterRepository extends JpaRepository<Supporter, Long> {
}
