package com.example.spring_webflux.model.llmclient.gpt.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GptChoice {

    private String finish_reason;
    private GptResponseMessageDto message;
    private GptResponseMessageDto delta;
}
