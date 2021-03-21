package com.junyoung.searchwheretogoapi.controller;

import com.junyoung.searchwheretogoapi.model.api.PageParam;
import com.junyoung.searchwheretogoapi.model.common.ApiResponse;
import com.junyoung.searchwheretogoapi.model.data.PlaceSearchHistory;
import com.junyoung.searchwheretogoapi.model.data.User;
import com.junyoung.searchwheretogoapi.service.search.PlaceSearchHistoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1.0")
public class PlaceSearchHistoryController {
    private final PlaceSearchHistoryService placeSearchHistoryService;

    @GetMapping("/places/histories")
    public ApiResponse<List<PlaceSearchHistory>> getPlaceSearchHistories(
            @AuthenticationPrincipal User user, PageParam pageParam) {
        log.debug("> getPlaceSearchHistories(user={}, pageParam={})", user, pageParam);

        return ApiResponse.success(
                placeSearchHistoryService.getPlaceSearchHistories(user, pageParam));
    }
}