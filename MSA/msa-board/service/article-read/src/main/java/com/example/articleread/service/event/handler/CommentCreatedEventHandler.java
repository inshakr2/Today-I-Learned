package com.example.articleread.service.event.handler;

import com.example.articleread.repository.ArticleQueryModelRepository;
import com.example.common.event.Event;
import com.example.common.event.EventType;
import com.example.common.event.payload.ArticleDeletedEventPayload;
import com.example.common.event.payload.CommentCreatedEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentCreatedEventHandler implements EventHandler<CommentCreatedEventPayload>{
    private final ArticleQueryModelRepository articleQueryModelRepository;

    @Override
    public void handle(Event<CommentCreatedEventPayload> event) {
        articleQueryModelRepository.read(event.getPayload().getArticleId())
            .ifPresent(articleQueryModel -> {
                articleQueryModel.updateBy(event.getPayload());
                articleQueryModelRepository.update(articleQueryModel);
            });
    }

    @Override
    public boolean supports(Event<CommentCreatedEventPayload> event) {
        return EventType.COMMENT_CREATED == event.getType();
    }
}
