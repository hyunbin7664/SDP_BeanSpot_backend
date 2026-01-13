package com.beanspot.backend.repository;

import com.beanspot.backend.entity.announcement.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Long> {
}
