package com.example.like.controller;

import com.example.like.service.ArticleLikeService;
import com.example.like.service.response.ArticleLikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ArticleLikeController {

    private final ArticleLikeService articleLikeService;

    @GetMapping("/v1/article-like/articles/{articleId}/users/{userId}")
    public ArticleLikeResponse read(@PathVariable("articleId") Long articleId,
                                    @PathVariable("userId") Long userId) {
        return articleLikeService.read(articleId, userId);
    }

    @PostMapping("/v1/article-like/articles/{articleId}/users/{userId}")
    public void like(@PathVariable("articleId") Long articleId,
                     @PathVariable("userId") Long userId) {
        articleLikeService.likePessimisticLock1(articleId, userId);
    }

    @DeleteMapping("/v1/article-like/articles/{articleId}/users/{userId}")
    public void unlike(@PathVariable("articleId") Long articleId,
                     @PathVariable("userId") Long userId) {
        articleLikeService.unlikePessimisticLock1(articleId, userId);
    }


}
