package com.example.spring_webflux.model.llmclient.gpt.response;

import com.example.spring_webflux.exception.CustomErrorType;
import com.example.spring_webflux.exception.ErrorTypeException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GptChatResponseDto {

    private List<GptChoice> choices;

    public GptChoice getSingleChoice() {
        return choices.stream().findFirst().orElseThrow(() ->
            new ErrorTypeException("[GptResponse : There is no choices]", CustomErrorType.GPT_RESPONSE_ERROR)
        );
    }
}
