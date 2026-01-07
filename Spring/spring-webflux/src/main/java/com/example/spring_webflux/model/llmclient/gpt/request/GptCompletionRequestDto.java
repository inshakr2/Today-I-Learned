package com.example.spring_webflux.model.llmclient.gpt.request;

import com.example.spring_webflux.model.llmclient.gpt.GptMessageRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GptCompletionRequestDto {

    private GptMessageRole role;
    private String content;
}
