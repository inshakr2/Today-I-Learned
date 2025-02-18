package com.example.article.service;

import com.example.article.entity.Article;
import com.example.article.repository.ArticleRepository;
import com.example.article.service.request.ArticleCreateRequest;
import com.example.article.service.request.ArticleUpdateRequest;
import com.example.article.service.response.ArticlePageResponse;
import com.example.article.service.response.ArticleResponse;
import com.example.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final Snowflake snowflake = new Snowflake();
    private final ArticleRepository articleRepository;

    @Transactional
    public ArticleResponse create(ArticleCreateRequest request) {
        Article article = articleRepository.save(
                Article.create(snowflake.nextId(), request.getTitle(), request.getContent(), request.getBoardId(), request.getWriterId())
        );

        return ArticleResponse.from(article);
    }

    @Transactional
    public ArticleResponse update(Long articleId, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        article.update(request.getTitle(), request.getContent());

        return ArticleResponse.from(article);
    }

    public ArticleResponse read(Long articleId) {
        return ArticleResponse.from(articleRepository.findById(articleId).orElseThrow());
    }

    @Transactional
    public void delete(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    public ArticlePageResponse readAll(Long boardId, Long page, Long pageSize) {
        return ArticlePageResponse.of(
                articleRepository.findAll(boardId, (page - 1) * pageSize, pageSize)
                        .stream().map(ArticleResponse::from).toList(),
                articleRepository.count(
                        boardId,
                        PageLimitCalculator.calculatePageLimit(page, pageSize, 10L))
        );
    }
}
