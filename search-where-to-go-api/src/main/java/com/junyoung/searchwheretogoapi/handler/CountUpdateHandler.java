package com.junyoung.searchwheretogoapi.handler;

import com.junyoung.searchwheretogoapi.model.data.SearchCount;
import com.junyoung.searchwheretogoapi.repository.PlaceSearchCountRepository;
import com.junyoung.searchwheretogoapi.service.place.SearchCounter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@RequiredArgsConstructor
@Component
public class CountUpdateHandler {

  private final SearchCounter searchCounter;
  private final PlaceSearchCountRepository placeSearchCountRepository;

  @Scheduled(fixedDelayString = "${count.update.interval:1000}")
  public void poll() {
    List<SearchCount> searchCounts = searchCounter.getSearchedCounts();
    if (!CollectionUtils.isEmpty(searchCounts)) {
      searchCounts.forEach(
          searchCount -> {
            long previousCount =
                placeSearchCountRepository
                    .findById(searchCount.getQuery())
                    .map(SearchCount::getCount)
                    .orElse(0L);
            searchCount.addCount(previousCount);
            placeSearchCountRepository.save(searchCount);
          });
      searchCounter.clearCounter();
    }
  }
}
