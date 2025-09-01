package com.bduo.newsbrief.service;

import com.bduo.newsbrief.dto.NewsSummaryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NewsBriefService {

    private final NewsApiClient newsApiClient;

    public NewsSummaryResponse generateGeneralNewsBrief() {
        final NewsApiResponse newsApiResponse = newsApiClient.getTopHeadlines();
    }
}
