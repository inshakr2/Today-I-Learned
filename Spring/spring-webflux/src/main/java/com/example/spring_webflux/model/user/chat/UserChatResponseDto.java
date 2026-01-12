package com.example.spring_webflux.model.user.chat;

import com.example.spring_webflux.exception.CommonError;
import com.example.spring_webflux.model.llmclient.LlmChatResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserChatResponseDto {

    private String title;
    private String response;
    private CommonError error;

    public boolean isValid() {
        return Optional.ofNullable(error).isEmpty();
    }

    public UserChatResponseDto(String title, String response) {
        this.title = title;
        this.response = response;
    }

    public UserChatResponseDto(LlmChatResponseDto llmChatResponseDto) {
        this.title = llmChatResponseDto.getTitle();
        this.response = llmChatResponseDto.getLlmResponse();
        this.error = llmChatResponseDto.getError();
    }
}
