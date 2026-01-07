package com.example.spring_webflux.model.user.chat;

import com.example.spring_webflux.model.llmclient.LlmModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserChatRequestDto {

    private String request;
    private LlmModel llmModel;

}
