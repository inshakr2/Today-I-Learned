package com.example.user_service.controller;

import com.example.user_service.dto.UserDto;
import com.example.user_service.jpa.UserEntity;
import com.example.user_service.service.UserService;
import com.example.user_service.vo.RequestUser;
import com.example.user_service.vo.ResponseUser;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/user-service")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final Environment env;

    @Value("${greeting.message}")
    private String welcomeMessage;

    @GetMapping("/health_check")
    @Timed(value = "users.status", longTask = true)
    public String status() {
        return String.format("It's Working in User Service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("server.port")
                + ", token secret=" + env.getProperty("token.secret")
                + ", token expiration time=" + env.getProperty("token.expiration_time"));
    }

    @GetMapping("/welcome")
    @Timed(value = "users.welcome", longTask = true)
    public String welcome() {
        return welcomeMessage;
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody RequestUser requestUser) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(requestUser, UserDto.class);
        userService.createUser(userDto);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserEntity> userList = userService.getUserByAll();

        ModelMapper mapper = new ModelMapper();
        List<ResponseUser> result = new ArrayList<>();

        userList.forEach(u ->result.add(mapper.map(u, ResponseUser.class)));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseUser> getUsers(@PathVariable("userId") String userId) {
        UserDto userDto = userService.getUserByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelMapper().map(userDto, ResponseUser.class)
        );
    }
}
