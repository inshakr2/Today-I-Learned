package com.example.view.service;

import com.example.view.repository.ArticleViewCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleViewService {

    private final ArticleViewCountRepository articleViewCountRepository;

    public Long increase(Long articleId, Long userId) {
        return articleViewCountRepository.increase(articleId);
    }

    public Long count(Long articleId) {
        return articleViewCountRepository.read(articleId);
    }

}
