package com.example.spring_webflux.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
        jakarta / javax = 서블릿 객체를 받지만 webFlux에서는 exchange를 받음
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ErrorResponse> handleGeneralException(Exception ex, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        log.error("[GeneralException] Request URI : {}, Method : {}, Error: {}",
                                request.getURI(), request.getMethod(), ex.getMessage(), ex);
        CommonError commonError = new CommonError("500", ex.getMessage());
        return Mono.just(new ErrorResponse(commonError));
    }

    @ExceptionHandler(ErrorTypeException.class)
    public Mono<ErrorResponse> handleErrorTypeException(ErrorTypeException ex, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        log.error("[GeneralException] Request URI : {}, Method : {}, Error: {}",
                request.getURI(), request.getMethod(), ex.getMessage(), ex);
        CommonError commonError = new CommonError(ex.getErrorType().getCode(), ex.getMessage());
        return Mono.just(new ErrorResponse(commonError));
    }
}
