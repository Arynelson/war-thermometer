package com.war_thermometer.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "news-api", url = "https://newsapi.org/v2")
public interface NewsApiService {

    @GetMapping("/everything")
    NewsApiResponse getNews(
            @RequestParam("q") String query,
            @RequestParam("apiKey") String apiKey,
            @RequestParam("language") String language
    );

    record NewsApiResponse(String status, int totalResults, List<Article> articles) {}
    record Article(String title, String description) {}
}
