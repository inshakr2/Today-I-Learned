package com.example.spring_webflux.service.facade;

import com.example.spring_webflux.model.facade.FacadeAvailableModel;
import com.example.spring_webflux.model.facade.FacadeHomeResponseDto;
import com.example.spring_webflux.model.llmclient.LlmModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
public class FacadeServiceImpl implements FacadeService{

    @Override
    public Mono<FacadeHomeResponseDto> getFacadeHomeResponseDto() {
        return Mono.fromCallable(() -> {
            List<FacadeAvailableModel> availableModelList = Arrays.stream(LlmModel.values())
                    .map(availableModel -> new FacadeAvailableModel(availableModel.name(), availableModel.getCode())).toList();

            return new FacadeHomeResponseDto(availableModelList);
        });
    }
}
