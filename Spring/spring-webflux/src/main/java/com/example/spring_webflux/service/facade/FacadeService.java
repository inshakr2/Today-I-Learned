package com.example.spring_webflux.service.facade;

import com.example.spring_webflux.model.facade.FacadeHomeResponseDto;
import reactor.core.publisher.Mono;

public interface FacadeService {
    Mono<FacadeHomeResponseDto> getFacadeHomeResponseDto();
}
