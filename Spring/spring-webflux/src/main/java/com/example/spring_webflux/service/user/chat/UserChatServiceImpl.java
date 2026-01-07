package com.example.spring_webflux.service.user.chat;

import com.example.spring_webflux.model.llmclient.LlmChatRequestDto;
import com.example.spring_webflux.model.llmclient.LlmChatResponseDto;
import com.example.spring_webflux.model.llmclient.LlmType;
import com.example.spring_webflux.model.user.chat.UserChatRequestDto;
import com.example.spring_webflux.model.user.chat.UserChatResponseDto;
import com.example.spring_webflux.service.llmclient.LlmWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserChatServiceImpl implements UserChatService {

    private final Map<LlmType, LlmWebClientService> llmWebClientServiceMap;

    @Override
    public Mono<UserChatResponseDto> getOneShotChat(UserChatRequestDto userChatRequestDto) {
//        간단한 로직때문에 코드 가독성이 떨어진다. 아래처럼 단순한 맵핑 로직이라면 뭐 단순하게 Mono 랩핑은 필요 없을 수도 있다.
//        LlmChatRequestDto llmChatRequestDto = new LlmChatRequestDto(userChatRequestDto, "요청에 적절히 응답해주세요.");
//        Mono<LlmChatResponseDto> chatCompletionMono = llmWebClientServiceMap.get(userChatRequestDto.getLlmModel().getLlmType())
//                .getChatCompletion(llmChatRequestDto);
//        return chatCompletionMono.map(UserChatResponseDto::new);
        return Mono.defer(() -> {
            LlmChatRequestDto llmChatRequestDto = new LlmChatRequestDto(userChatRequestDto, "요청에 적절히 응답해주세요.");
            Mono<LlmChatResponseDto> chatCompletionMono = llmWebClientServiceMap.get(userChatRequestDto.getLlmModel().getLlmType())
                    .getChatCompletion(llmChatRequestDto);
            return chatCompletionMono.map(UserChatResponseDto::new);
        });
    }
}
