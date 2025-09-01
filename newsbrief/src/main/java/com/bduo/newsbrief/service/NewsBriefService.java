package com.bduo.newsbrief.service;

import com.bduo.newsbrief.client.NewsApiClient;
import com.bduo.newsbrief.client.OllamaClient;
import com.bduo.newsbrief.dto.NewsApiResponse;
import com.bduo.newsbrief.dto.NewsSummaryResponse;
import com.bduo.newsbrief.dto.OllamaResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NewsBriefService {

    private final NewsApiClient newsApiClient;
    private final OllamaClient ollamaClient;

    @Autowired
    public NewsBriefService(NewsApiClient newsApiClient, OllamaClient ollamaClient) {
        this.newsApiClient = newsApiClient;
        this.ollamaClient = ollamaClient;
    }

    public NewsSummaryResponse generateGeneralNewsBrief() {
        final NewsApiResponse newsApiResponse = newsApiClient.getTopHeadlines();
        log.info("Request summary for {} articles", newsApiResponse.getArticles().size());

        final OllamaResponse ollamaResponse =
                ollamaClient.generateSummary(newsApiResponse.getArticles());

        return NewsSummaryResponse.builder()
                .createdAt(java.time.LocalDateTime.now())
                .summary(ollamaResponse.getResponse())
                .build();
    }
}
