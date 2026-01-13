package com.beanspot.backend.repository;

import com.beanspot.backend.entity.announcement.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
}
