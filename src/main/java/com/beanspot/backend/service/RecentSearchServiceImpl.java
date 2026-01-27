package com.beanspot.backend.service;

import com.beanspot.backend.entity.search.SearchHistory;
import com.beanspot.backend.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RecentSearchServiceImpl implements RecentSearchService {
    private static final int MAX_RECENT_SEARCH_SIZE = 5;

    private final SearchHistoryRepository repository;

    @Override
    public void saveRecentSearch(Long userId, String keyword) {
        repository.findByUserIdAndKeyword(userId, keyword)
                .ifPresentOrElse(
                        history -> history.touch(),
                        () -> repository.save(SearchHistory.create(userId, keyword))
                );

//        repository.deleteOverflow(userId, MAX_RECENT_SEARCH_SIZE);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getRecentSearch(Long userId) {
        return repository.findTop5ByUserIdOrderByUpdatedAtDesc(userId)
                .stream()
                .map(SearchHistory::getKeyword)
                .toList();
    }

    @Override
    public void deleteRecentSearch(Long userId, String keyword) {
        repository.findByUserIdAndKeyword(userId, keyword)
                .ifPresent(repository::delete);
    }
}
