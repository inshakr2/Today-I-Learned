package com.example.spring_webflux.service.llmclient;

import com.example.spring_webflux.model.llmclient.LlmChatRequestDto;
import com.example.spring_webflux.model.llmclient.LlmChatResponseDto;
import com.example.spring_webflux.model.llmclient.LlmType;
import reactor.core.publisher.Mono;

public interface LlmWebClientService {
    Mono<LlmChatResponseDto> getChatCompletion(LlmChatRequestDto requestDto);

    LlmType getLlmType();
    // gpt/gemini webClient

}
