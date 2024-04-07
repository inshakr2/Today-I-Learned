package org.example.cache.controller;

import lombok.RequiredArgsConstructor;
import org.example.cache.domain.entity.RedisHashUser;
import org.example.cache.domain.entity.User;
import org.example.cache.domain.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/hash-user/{id}")
    public RedisHashUser getHashUser(@PathVariable Long id) {
        return userService.getHashUser(id);
    }

    @GetMapping("/cacheable-user/{id}")
    public User getCacheableUser(@PathVariable Long id) {
        return userService.getCacheableUser(id);
    }
}
