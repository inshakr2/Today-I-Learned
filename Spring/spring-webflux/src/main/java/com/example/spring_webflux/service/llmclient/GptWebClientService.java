package com.example.spring_webflux.service.llmclient;

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
import reactor.core.publisher.Mono;

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
                        return Mono.error(new RuntimeException("API 요청 실패 : " + body));
                    });
                }))
                .bodyToMono(GptChatResponseDto.class)
                .map(LlmChatResponseDto::new)
                ;

    }

    @Override
    public LlmType getLlmType() {
        return LlmType.GPT;
    }
}
