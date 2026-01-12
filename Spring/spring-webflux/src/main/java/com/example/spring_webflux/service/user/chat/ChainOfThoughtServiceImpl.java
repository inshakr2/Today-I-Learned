package com.example.spring_webflux.service.user.chat;

import com.example.spring_webflux.exception.CustomErrorType;
import com.example.spring_webflux.exception.ErrorTypeException;
import com.example.spring_webflux.model.llmclient.LlmChatRequestDto;
import com.example.spring_webflux.model.llmclient.LlmChatResponseDto;
import com.example.spring_webflux.model.llmclient.LlmModel;
import com.example.spring_webflux.model.llmclient.LlmType;
import com.example.spring_webflux.model.llmclient.jsonformat.AnswerListResponseDto;
import com.example.spring_webflux.model.user.chat.UserChatRequestDto;
import com.example.spring_webflux.model.user.chat.UserChatResponseDto;
import com.example.spring_webflux.service.llmclient.LlmWebClientService;
import com.example.spring_webflux.util.ChatUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChainOfThoughtServiceImpl implements ChainOfThoughtService{

    private final ObjectMapper objectMapper;
    private final Map<LlmType, LlmWebClientService> llmWebClientServiceMap;

    /*
    1. 사용자의 요청을 효율적으로 분석하기 위한 단계를 LLM에게 물어봄
     -> answerList : 분석 단계를 LLM이 응답

    2. 분석 단계 별로 LLM에게 요청을 보내어 상세하게 분석

    3. 단계별로 분석된 응답을 종합하여 최종 응답
     */
    @Override
    public Flux<UserChatResponseDto> getChainOfThought(UserChatRequestDto userChatRequestDto) {
        return Flux.create(sink -> {
            String userRequest = userChatRequestDto.getRequest();
            LlmModel requestModel = userChatRequestDto.getLlmModel();

            // 1
            String establishingThoughtChainPrompt = String.format("""
                    다음은 사용자의 입력입니다. : "%s"
                    사용자에게 체계적으로 답변하기 위해 어떤 단계들이 필요할지 정리해주세요.
                    """, userRequest);

            String establishingThoughtChainSystemPrompt = """
                            아래처럼 List<String> answerList의 형태를 가지는 JSON FORMAT으로 응답해주세요. 
                            <JSONSCHEMA>
                                {
                                    "answerList" : ["", ...]
                                }
                            </JSONSCHEMA>
                    """;
            LlmChatRequestDto llmChatRequestDto = new LlmChatRequestDto(
                    establishingThoughtChainPrompt, establishingThoughtChainSystemPrompt, true, requestModel);

            LlmWebClientService llmWebClientService = llmWebClientServiceMap.get(requestModel.getLlmType());
            Mono<AnswerListResponseDto> cotStepListMono  = llmWebClientService.getChatCompletion(llmChatRequestDto) //
                    .map(response -> {
                        String llmResponse = response.getLlmResponse();
                        String extractedJsonString = ChatUtils.extractJsonString(llmResponse);

                        try {
                            AnswerListResponseDto answerListResponseDto = objectMapper.readValue(extractedJsonString, AnswerListResponseDto.class);
//                            sink.next(new UserChatResponseDto("필요한 작업 단계 분석", answerListResponseDto.toString()));
                            return answerListResponseDto;
                        } catch (JsonProcessingException e) {
                            throw new ErrorTypeException("[JsonParseError] json parse error. extractedJson : " + extractedJsonString, CustomErrorType.LLM_RESPONSE_JSON_PARSE_ERROR);
                        }
                    }).doOnNext(publishedData -> sink.next(new UserChatResponseDto("필요한 작업 단계 분석", publishedData.toString())));

            Flux<String> cotStepFlux = cotStepListMono.flatMapMany(cotStepList -> Flux.fromIterable(cotStepList.getAnswerList()));

            // 2
            Flux<String> analyzedCotStep = cotStepFlux.flatMapSequential(cotStep -> {
                String cotStepRequestPrompt = String.format("""
                        다음은 사용자의 입력입니다 : %s
                        
                        사용자의 요구를 다음 단계에 따라 분석해주세요 : %s
                        """, userRequest, cotStep);
                LlmChatRequestDto llmDetailChatRequestDto = new LlmChatRequestDto(
                        cotStepRequestPrompt, "", false, requestModel);
                return llmWebClientService.getChatCompletionWithCatchException(llmDetailChatRequestDto)
                        .map(LlmChatResponseDto::getLlmResponse);
            }).doOnNext(publishedData -> sink.next(new UserChatResponseDto("단계별 분석", publishedData)));

            // 3
            Mono<String> finalAnswerMono = analyzedCotStep.collectList().flatMap(stepPromptList -> {
                String concatStepPrompt = String.join("\n", stepPromptList);
                String finalAnswerPrompt = String.format("""
                        다음은 사용자의 입력입니다 : %s
                        아래 사항들을 참고, 분석하여 사용자의 입력에 대한 최종 답변을 해주세요:
                        %s
                        """, userRequest, concatStepPrompt);
                LlmChatRequestDto llmFinalChatRequestDto = new LlmChatRequestDto(
                        finalAnswerPrompt, "", false, requestModel);
                return llmWebClientService.getChatCompletionWithCatchException(llmFinalChatRequestDto)
                        .map(LlmChatResponseDto::getLlmResponse);
            });

            finalAnswerMono.subscribe(finalAnswer -> {
                sink.next(new UserChatResponseDto("최종 응답", finalAnswer));
                sink.complete();
            }, error -> {
                log.error("[COT] cot response error", error);
                sink.error(error);
            });
        });
    }
}
