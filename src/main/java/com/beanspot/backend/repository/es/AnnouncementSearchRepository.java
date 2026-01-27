package com.beanspot.backend.repository.es;

import com.beanspot.backend.entity.AnnouncementDocument;
import com.beanspot.backend.entity.announcement.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface AnnouncementSearchRepository extends ElasticsearchRepository<AnnouncementDocument, Long>, AnnouncementSearchRepositoryCustom {

}
