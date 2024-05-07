package com.example.webflux1.controller;

import com.example.webflux1.dto.UserCreateRequest;
import com.example.webflux1.dto.UserResponse;
import com.example.webflux1.dto.UserUpdateRequest;
import com.example.webflux1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public Mono<UserResponse> createUser(@RequestBody UserCreateRequest userCreateRequest) {
        return userService.create(userCreateRequest.getName(), userCreateRequest.getEmail())
                .map(UserResponse::of);
    }

    @GetMapping
    public Flux<UserResponse> findAllUsers() {
        return userService.findAll()
                .map(UserResponse::of);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> findUser(@PathVariable Long id) {
        return userService.findById(id)
                .map(u -> ResponseEntity.ok(UserResponse.of(u)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<?>> deleteUser(@PathVariable Long id) {
        return userService.deleteById(id).then(
                Mono.just(ResponseEntity.noContent().build())
        );
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> findUser(@PathVariable Long id,
                                       @RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.update(id, userUpdateRequest.getName(), userUpdateRequest.getEmail())
                .map(u -> ResponseEntity.ok(UserResponse.of(u)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

}
