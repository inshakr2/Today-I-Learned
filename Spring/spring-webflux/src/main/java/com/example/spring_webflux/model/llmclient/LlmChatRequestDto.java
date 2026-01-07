package com.example.spring_webflux.model.llmclient;

import com.example.spring_webflux.model.user.chat.UserChatRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LlmChatRequestDto {

    private String userRequest;
    /*
    systemPrompt가 userRequest에 포함되는 내용보다 더 높은 강제성과 우선순위를 가진다.
     */
    private String systemPrompt;
    private boolean useJson;
    private LlmModel llmModel;

    public LlmChatRequestDto(UserChatRequestDto userChatRequestDto, String systemPrompt) {
        this.userRequest = userChatRequestDto.getRequest();
        this.llmModel = userChatRequestDto.getLlmModel();
        this.systemPrompt = systemPrompt;
    }
}
