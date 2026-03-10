package com.example.articleread.service.event.handler;

import com.example.articleread.repository.ArticleQueryModelRepository;
import com.example.common.event.Event;
import com.example.common.event.EventType;
import com.example.common.event.payload.ArticleDeletedEventPayload;
import com.example.common.event.payload.ArticleUpdatedEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleDeletedEventHandler implements EventHandler<ArticleDeletedEventPayload>{
    private final ArticleQueryModelRepository articleQueryModelRepository;

    @Override
    public void handle(Event<ArticleDeletedEventPayload> event) {
        articleQueryModelRepository.delete(event.getPayload().getArticleId());
    }

    @Override
    public boolean supports(Event<ArticleDeletedEventPayload> event) {
        return EventType.ARTICLE_DELETED == event.getType();
    }
}
