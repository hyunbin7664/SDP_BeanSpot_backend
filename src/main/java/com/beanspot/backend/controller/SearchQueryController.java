package com.beanspot.backend.controller;

import com.beanspot.backend.common.response.ApiResponse;
import com.beanspot.backend.security.CurrentUserId;
import com.beanspot.backend.service.search.RecentSearchService;
import com.beanspot.backend.service.search.SearchKeywordFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name="검색 UX 관련 API", description = "최근 검색어 및 검색어 자동완성 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchQueryController {


    private final SearchKeywordFacade searchKeywordFacade;

    @GetMapping("/recent")
    public ApiResponse<List<String>> recent(@CurrentUserId Long userId) {
        return ApiResponse.ok(searchKeywordFacade.getRecent(userId));
    }

    @DeleteMapping("/recent")
    public ApiResponse<?> deleteRecent(@CurrentUserId Long userId, @RequestParam String keyword) {
        searchKeywordFacade.deleteRecent(userId, keyword);
        return ApiResponse.ok("키워드 삭제가 처리되었습니다.");
    }

    @DeleteMapping("/recent/all")
    public ApiResponse<?> deleteRecentAll(@CurrentUserId Long userId) {
        searchKeywordFacade.deleteAllRecent(userId);
        return ApiResponse.ok("최근 검색어가 모두 삭제되었습니다.");
    }

    @GetMapping("/autocomplete")
    public ApiResponse<List<String>> autocomplete( @RequestParam String query) {
        return ApiResponse.ok(searchKeywordFacade.getAutoComplete(query));
    }

    @GetMapping("/recommend")
    public ApiResponse<List<String>> recommend() {
        return ApiResponse.ok(searchKeywordFacade.getRecommended(5));
    }
}
