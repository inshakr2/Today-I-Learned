package com.example.spring_webflux.service.user.chat;

import com.example.spring_webflux.model.user.chat.UserChatRequestDto;
import com.example.spring_webflux.model.user.chat.UserChatResponseDto;
import reactor.core.publisher.Mono;

public interface UserChatService {
    Mono<UserChatResponseDto> getOneShotChat(UserChatRequestDto userChatRequestDto);
}
