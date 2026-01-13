package com.beanspot.backend.repository;

import com.beanspot.backend.entity.announcement.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long>, AnnouncementCustomRepository{
}
