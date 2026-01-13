package com.beanspot.backend.repository;

import com.beanspot.backend.entity.announcement.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnnouncementCustomRepository {

    Page<Announcement> searchWithFilters(
            String keyword,
            String type,
            String region,
            String sort,
            Pageable pageable
    );

    List<Announcement> findTopByFilters(
            String keyword,
            String type,
            String region,
            String sort,
            int limit
    );

}
