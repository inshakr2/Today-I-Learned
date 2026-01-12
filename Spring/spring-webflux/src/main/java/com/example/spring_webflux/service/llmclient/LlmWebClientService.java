package com.example.spring_webflux.service.llmclient;

import com.example.spring_webflux.exception.CommonError;
import com.example.spring_webflux.exception.ErrorTypeException;
import com.example.spring_webflux.model.llmclient.LlmChatRequestDto;
import com.example.spring_webflux.model.llmclient.LlmChatResponseDto;
import com.example.spring_webflux.model.llmclient.LlmType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LlmWebClientService {
    Mono<LlmChatResponseDto> getChatCompletion(LlmChatRequestDto requestDto);

    LlmType getLlmType();

    Flux<LlmChatResponseDto> getChatCompletionStream(LlmChatRequestDto requestDto);
    // gpt/gemini webClient

    default Mono<LlmChatResponseDto> getChatCompletionWithCatchException(LlmChatRequestDto requestDto) {
        return getChatCompletion(requestDto)
                .onErrorResume(exception -> {
                    if (exception instanceof ErrorTypeException errorTypeException) {
                        CommonError commonError = new CommonError(errorTypeException.getErrorType().getCode(), errorTypeException.getMessage());
                        return Mono.just(new LlmChatResponseDto(commonError, errorTypeException));
                    } else {
                        CommonError commonError = new CommonError(500, exception.getMessage());
                        return Mono.just(new LlmChatResponseDto(commonError, exception));
                    }
                });
    }
}
