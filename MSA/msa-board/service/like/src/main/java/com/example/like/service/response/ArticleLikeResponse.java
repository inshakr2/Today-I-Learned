package com.example.like.service.response;

import com.example.like.entity.ArticleLike;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ArticleLikeResponse {

    private Long articleLikeId;
    private Long articleId;
    private Long userId;
    private LocalDateTime createdAt;

    public static ArticleLikeResponse from(ArticleLike articleLike) {
        ArticleLikeResponse articleLikeResponse = new ArticleLikeResponse();
        articleLikeResponse.articleLikeId = articleLike.getArticleLikeId();
        articleLikeResponse.articleId = articleLike.getArticleId();
        articleLikeResponse.userId = articleLikeResponse.getUserId();
        articleLikeResponse.createdAt = articleLike.getCreatedAt();
        return articleLikeResponse;
    }

}
