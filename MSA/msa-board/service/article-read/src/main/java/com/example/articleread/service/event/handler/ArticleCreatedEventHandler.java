package com.example.articleread.service.event.handler;

import com.example.articleread.repository.ArticleQueryModel;
import com.example.articleread.repository.ArticleQueryModelRepository;
import com.example.common.event.Event;
import com.example.common.event.EventType;
import com.example.common.event.payload.ArticleCreatedEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class ArticleCreatedEventHandler implements EventHandler<ArticleCreatedEventPayload>{
    private final ArticleQueryModelRepository articleQueryModelRepository;

    @Override
    public void handle(Event<ArticleCreatedEventPayload> event) {
        articleQueryModelRepository.create(
                ArticleQueryModel.create(event.getPayload()),
                Duration.ofDays(1)
        );
    }

    @Override
    public boolean supports(Event<ArticleCreatedEventPayload> event) {
        return EventType.ARTICLE_CREATED == event.getType();
    }
}
