package com.example.spring_webflux.service.llmclient;

import com.example.spring_webflux.exception.CommonError;
import com.example.spring_webflux.exception.CustomErrorType;
import com.example.spring_webflux.exception.ErrorTypeException;
import com.example.spring_webflux.model.llmclient.LlmChatRequestDto;
import com.example.spring_webflux.model.llmclient.LlmChatResponseDto;
import com.example.spring_webflux.model.llmclient.LlmType;
import com.example.spring_webflux.model.llmclient.gemini.request.GeminiChatRequestDto;
import com.example.spring_webflux.model.llmclient.gemini.response.GeminiChatResponseDto;
import com.example.spring_webflux.model.llmclient.gpt.response.GptChatResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeminiWebClientService implements LlmWebClientService{

    private final WebClient webClient;
    @Value("${llm.gemini.key}")
    private String geminiApiKey;

    @Override
    public Mono<LlmChatResponseDto> getChatCompletion(LlmChatRequestDto requestDto) {
        GeminiChatRequestDto geminiChatRequestDto = new GeminiChatRequestDto(requestDto);
        return webClient.post()
                .uri("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + geminiApiKey)
                .bodyValue(geminiChatRequestDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (clientResponse -> {
                    return clientResponse.bodyToMono(String.class).flatMap(body -> {
                        log.error("Error Response : " + body);
                        return Mono.error(new ErrorTypeException("API 요청 실패 : " + body, CustomErrorType.GEMINI_RESPONSE_ERROR));
                    });
                }))
                .bodyToMono(GeminiChatResponseDto.class)
                .map(LlmChatResponseDto::new);
    }

    @Override
    public Flux<LlmChatResponseDto> getChatCompletionStream(LlmChatRequestDto requestDto) {
        GeminiChatRequestDto geminiChatRequestDto = new GeminiChatRequestDto(requestDto);
        return webClient.post()
                .uri("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:streamGenerateContent?key=" + geminiApiKey)
                .bodyValue(geminiChatRequestDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (clientResponse -> {
                    return clientResponse.bodyToMono(String.class).flatMap(body -> {
                        log.error("Error Response : " + body);
                        return Mono.error(new ErrorTypeException("API 요청 실패 : " + body, CustomErrorType.GEMINI_RESPONSE_ERROR));
                    });
                }))
                .bodyToFlux(GeminiChatResponseDto.class)
//                .map(LlmChatResponseDto::new)
                .map(response -> {
                    // 데이터 스트림 반환 중 에러 발생 시 별도의 데이터 객체로 반환. 프론트에서도 이러한 데이터 셋팅 해줄 수 있어야 함.
                    try {
                        return new LlmChatResponseDto(response);
                    } catch (Exception e) {
                        log.error("[getChatCompletionStream] Error Occurred During Gemini Stream");
                        return new LlmChatResponseDto(
                                new CommonError(CustomErrorType.GEMINI_RESPONSE_ERROR.getCode(), e.getMessage())
                        );
                    }
                });
    }

    @Override
    public LlmType getLlmType() {
        return LlmType.GEMINI;
    }
}
