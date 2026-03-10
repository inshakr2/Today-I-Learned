package com.example.articleread.service.event.handler;

import com.example.articleread.repository.ArticleQueryModelRepository;
import com.example.common.event.Event;
import com.example.common.event.EventType;
import com.example.common.event.payload.CommentCreatedEventPayload;
import com.example.common.event.payload.CommentDeletedEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentDeletedEventHandler implements EventHandler<CommentDeletedEventPayload>{
    private final ArticleQueryModelRepository articleQueryModelRepository;

    @Override
    public void handle(Event<CommentDeletedEventPayload> event) {
        articleQueryModelRepository.read(event.getPayload().getArticleId())
            .ifPresent(articleQueryModel -> {
                articleQueryModel.updateBy(event.getPayload());
                articleQueryModelRepository.update(articleQueryModel);
            });
    }

    @Override
    public boolean supports(Event<CommentDeletedEventPayload> event) {
        return EventType.COMMENT_DELETED == event.getType();
    }
}
