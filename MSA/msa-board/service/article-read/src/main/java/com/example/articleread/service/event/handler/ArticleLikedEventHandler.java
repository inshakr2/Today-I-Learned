package com.example.articleread.service.event.handler;

import com.example.articleread.repository.ArticleQueryModelRepository;
import com.example.common.event.Event;
import com.example.common.event.EventType;
import com.example.common.event.payload.ArticleLikedEventPayload;
import com.example.common.event.payload.CommentCreatedEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleLikedEventHandler implements EventHandler<ArticleLikedEventPayload>{
    private final ArticleQueryModelRepository articleQueryModelRepository;

    @Override
    public void handle(Event<ArticleLikedEventPayload> event) {
        articleQueryModelRepository.read(event.getPayload().getArticleId())
            .ifPresent(articleQueryModel -> {
                articleQueryModel.updateBy(event.getPayload());
                articleQueryModelRepository.update(articleQueryModel);
            });
    }

    @Override
    public boolean supports(Event<ArticleLikedEventPayload> event) {
        return EventType.ARTICLE_LIKED == event.getType();
    }
}
