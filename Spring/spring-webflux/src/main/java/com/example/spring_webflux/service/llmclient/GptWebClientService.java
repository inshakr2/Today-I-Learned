package com.example.spring_webflux.service.llmclient;

import com.example.spring_webflux.exception.CustomErrorType;
import com.example.spring_webflux.exception.ErrorTypeException;
import com.example.spring_webflux.model.llmclient.LlmChatRequestDto;
import com.example.spring_webflux.model.llmclient.LlmChatResponseDto;
import com.example.spring_webflux.model.llmclient.LlmType;
import com.example.spring_webflux.model.llmclient.gpt.request.GptChatRequestDto;
import com.example.spring_webflux.model.llmclient.gpt.response.GptChatResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GptWebClientService implements LlmWebClientService{
    private final WebClient webClient;
    @Value("${llm.gpt.key}")
    private String gptApiKey;

    @Override
    public Mono<LlmChatResponseDto> getChatCompletion(LlmChatRequestDto requestDto) {
        GptChatRequestDto gptChatRequestDto = new GptChatRequestDto(requestDto);
        return webClient.post()
                .uri("https://api.openai.com/v1/chat/completions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + gptApiKey)
                .bodyValue(gptChatRequestDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (clientResponse -> {
                    return clientResponse.bodyToMono(String.class).flatMap(body -> {
                        log.error("Error Response : " + body);
                        return Mono.error(new ErrorTypeException("API 요청 실패 : " + body, CustomErrorType.GPT_RESPONSE_ERROR));
                    });
                }))
                .bodyToMono(GptChatResponseDto.class)
                .map(LlmChatResponseDto::new)
                ;
    }

    @Override
    public Flux<LlmChatResponseDto> getChatCompletionStream(LlmChatRequestDto requestDto) {
        GptChatRequestDto gptChatRequestDto = new GptChatRequestDto(requestDto);
        gptChatRequestDto.setStream(true);

        return webClient.post()
                .uri("https://api.openai.com/v1/chat/completions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + gptApiKey)
                .bodyValue(gptChatRequestDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (clientResponse -> {
                    return clientResponse.bodyToMono(String.class).flatMap(body -> {
                        log.error("Error Response : " + body);
                        return Mono.error(new ErrorTypeException("API 요청 실패 : " + body, CustomErrorType.GPT_RESPONSE_ERROR));
                    });
                }))
                .bodyToFlux(GptChatResponseDto.class)
                .takeWhile(response -> // 아래 조건이 만족될 때까지만 수신함. 그 이후로는 Flux 방출하지 않음
                        Optional.ofNullable(response.getSingleChoice().getFinish_reason()).isEmpty())
                .map(LlmChatResponseDto::getLlmChatResponseDtoFromStream)
                ;
    }

    @Override
    public LlmType getLlmType() {
        return LlmType.GPT;
    }
}
