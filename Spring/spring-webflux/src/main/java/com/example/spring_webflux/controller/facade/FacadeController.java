package com.example.spring_webflux.controller.facade;

import com.example.spring_webflux.model.facade.FacadeHomeResponseDto;
import com.example.spring_webflux.service.facade.FacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/facade")
@RequiredArgsConstructor
public class FacadeController {

    private final FacadeService facadeService;

    @PostMapping("/home")
    public Mono<FacadeHomeResponseDto> homeFacade() {
        return facadeService.getFacadeHomeResponseDto();
    }
}
