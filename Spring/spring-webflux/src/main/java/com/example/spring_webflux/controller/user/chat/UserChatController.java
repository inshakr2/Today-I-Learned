package com.example.spring_webflux.controller.user.chat;

import com.example.spring_webflux.model.user.chat.UserChatRequestDto;
import com.example.spring_webflux.model.user.chat.UserChatResponseDto;
import com.example.spring_webflux.service.user.chat.ChainOfThoughtService;
import com.example.spring_webflux.service.user.chat.UserChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class UserChatController {

    private final UserChatService userChatService;
    private final ChainOfThoughtService chainOfThoughtService;

    @PostMapping("/oneshot")
    public Mono<UserChatResponseDto> oneShotChat(@RequestBody UserChatRequestDto userChatRequestDto) {
        log.info("UserChatController.oneShotChat");
        return userChatService.getOneShotChat(userChatRequestDto);
    }

    @PostMapping("/oneshot/stream")
    public Flux<UserChatResponseDto> oneShotChatStream(@RequestBody UserChatRequestDto userChatRequestDto) {
        log.info("UserChatController.oneShotChatStream");
        return userChatService.getOneShotChatStream(userChatRequestDto);
    }

    @PostMapping("/cot")
    public Flux<UserChatResponseDto> chainOfThought(@RequestBody UserChatRequestDto userChatRequestDto) {
        log.info("UserChatController.chainOfThought");
        return chainOfThoughtService.getChainOfThought(userChatRequestDto);
    }
}
