package com.example.spring_webflux.model.llmclient;

import com.example.spring_webflux.exception.CommonError;
import com.example.spring_webflux.model.llmclient.gemini.response.GeminiChatResponseDto;
import com.example.spring_webflux.model.llmclient.gpt.response.GptChatResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Slf4j
public class LlmChatResponseDto {

    private String title;
    private String llmResponse;
    private CommonError error;

    public LlmChatResponseDto(String title, String llmResponse) {
        this.title = title;
        this.llmResponse = llmResponse;
    }

    public LlmChatResponseDto(String llmResponse) {
        this.llmResponse = llmResponse;
    }

    public LlmChatResponseDto(CommonError error) {
        log.error("[LlmResponseError] LlmResponseError : {}", error.getErrorMessage());
        this.error = error;
    }

    public LlmChatResponseDto(CommonError error, Throwable ex) {
        log.error("[LlmResponseError] LlmResponseError : {}", error, ex);
        this.error = error;
    }

    public LlmChatResponseDto(GptChatResponseDto gptChatResponseDto) {
        this.llmResponse = gptChatResponseDto.getSingleChoice().getMessage().getContent();
    }

    public static LlmChatResponseDto getLlmChatResponseDtoFromStream(GptChatResponseDto gptChatResponseDto) {
        return new LlmChatResponseDto(gptChatResponseDto.getSingleChoice().getDelta().getContent());
    }

    public LlmChatResponseDto(GeminiChatResponseDto geminiChatResponseDto) {
        this.llmResponse = geminiChatResponseDto.getSingleText();
    }
}
