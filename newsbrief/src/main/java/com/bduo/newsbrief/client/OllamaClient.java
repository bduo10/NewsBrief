package com.bduo.newsbrief.client;

import com.bduo.newsbrief.dto.Article;
import com.bduo.newsbrief.dto.OllamaRequest;
import com.bduo.newsbrief.dto.OllamaResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class OllamaClient {
    @Value("${ollama.base.url}")
    private String ollamaUrl;

    @Value("${ollama.mistral.model}")
    private String aiModel;

    public OllamaResponse generateSummary(final List<Article> articles) {
        final RestTemplate restTemplate = new RestTemplate();

        final String prompt = getPrompt(articles);
        final OllamaRequest requestPayload = OllamaRequest.builder()
                .model(aiModel)
                .prompt(prompt)
                .stream(false)
                .build();
        final HttpEntity<OllamaRequest> entity = getHttpEntity(requestPayload);

        final ResponseEntity<OllamaResponse> response =
                restTemplate.postForEntity(ollamaUrl, entity, OllamaResponse.class);
        log.info("ollama response {}", response.getBody());
        return response.getBody();
    }


    private static HttpEntity<OllamaRequest> getHttpEntity(final OllamaRequest requestPayload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OllamaRequest> entity = new HttpEntity<>(requestPayload, headers);
        return entity;
    }

    private String getPrompt(List<Article> articles) {
        final StringBuilder promptBuilder = new StringBuilder();

        promptBuilder.append("You are a news summarizer. " +
                "Summarize the top global news tories from today in a concise and informative way. " +
                "Focuse on major events, political developments, economic updates, and major technology or " +
                "Science breakthroughs. Keep the summary clear, objective, and easy to read like a daily news brief.");

        for (Article article : articles) {
            promptBuilder
                    .append("Title: " + article.getTitle() + "\n")
                    .append("Description: " + article.getDescription() + "\n")
                    .append("End of article \n\n");
        }
        final String prompt = promptBuilder.toString();
        return prompt;
    }

}
