package com.example.hotarticle.service.eventhandler;

import com.example.common.event.Event;
import com.example.common.event.EventPayload;

public interface EventHandler<T extends EventPayload> {
    void handle(Event<T> event);
    boolean supports(Event<T> event);
    Long findArticleId(Event<T> event);
}
