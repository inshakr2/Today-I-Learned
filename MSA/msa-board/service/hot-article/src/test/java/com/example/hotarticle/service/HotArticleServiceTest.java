package com.example.hotarticle.service;

import com.example.common.event.Event;
import com.example.common.event.EventType;
import com.example.hotarticle.service.eventhandler.EventHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotArticleServiceTest {
    @InjectMocks
    HotArticleService hotArticleService;
    @Mock
    List<EventHandler> eventHandlers;
    @Mock
    HotArticleScoreUpdater hotArticleScoreUpdater;

    @Test
    void handleEventIfEventHandlerNotFoundTest() {
        // given
        Event event = mock(Event.class);
        EventHandler eventHandler = mock(EventHandler.class);
        given(eventHandler.supports(event)).willReturn(false);
        given(eventHandlers.stream()).willReturn(Stream.of(eventHandler));

        // when
        hotArticleService.handleEvent(event);

        // then
        verify(eventHandler, never()).handle(event);
        verify(hotArticleScoreUpdater, never()).update(event, eventHandler);
    }

    @Test
    void handleEventIfArticleCreatedEvent() {
        // given
        Event event = mock(Event.class);
        given(event.getType()).willReturn(EventType.ARTICLE_CREATED);
        EventHandler eventHandler = mock(EventHandler.class);
        given(eventHandler.supports(event)).willReturn(true);
        given(eventHandlers.stream()).willReturn(Stream.of(eventHandler));

        // when
        hotArticleService.handleEvent(event);

        // then
        verify(eventHandler).handle(event);
        verify(hotArticleScoreUpdater, never()).update(event, eventHandler);
    }

    @Test
    void handleEventIfArticleDeletedEvent() {
        // given
        Event event = mock(Event.class);
        given(event.getType()).willReturn(EventType.ARTICLE_DELETED);
        EventHandler eventHandler = mock(EventHandler.class);
        given(eventHandler.supports(event)).willReturn(true);
        given(eventHandlers.stream()).willReturn(Stream.of(eventHandler));

        // when
        hotArticleService.handleEvent(event);

        // then
        verify(eventHandler).handle(event);
        verify(hotArticleScoreUpdater, never()).update(event, eventHandler);
    }

    @Test
    void handleEventIfScoreUpdatableEventTest() {
        // given
        Event event = mock(Event.class);
        given(event.getType()).willReturn(mock(EventType.class));
        EventHandler eventHandler = mock(EventHandler.class);
        given(eventHandler.supports(event)).willReturn(true);
        given(eventHandlers.stream()).willReturn(Stream.of(eventHandler));

        // when
        hotArticleService.handleEvent(event);

        // then
        verify(eventHandler, never()).handle(event);
        verify(hotArticleScoreUpdater).update(event, eventHandler);
    }

}