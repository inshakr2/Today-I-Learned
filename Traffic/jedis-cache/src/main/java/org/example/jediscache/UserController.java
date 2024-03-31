package org.example.jediscache;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final JedisPool jedisPool;

    @GetMapping("/users/{id}/email")
    public String getUserEmail(@PathVariable Long id) {

        try (Jedis jedis = jedisPool.getResource()) {

            String key = "users:%d:email".formatted(id);
            String userEmail = jedis.get(key);

            if (userEmail != null) {
                return userEmail;
            }

            userEmail = userRepository.findById(id).orElse(User.builder().build()).getEmail();
            jedis.set(key, userEmail);
            jedis.setex(key, 30, userEmail);
            return userEmail;
        }
    }
}
