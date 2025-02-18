package com.example.article.service.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class ArticlePageResponse {
    private List<ArticleResponse> articles;
    private Long articleCount;

    public static ArticlePageResponse of(List<ArticleResponse> articles, Long articleCount) {
        return ArticlePageResponse.builder()
                .articles(articles)
                .articleCount(articleCount)
                .build();
    }
}
