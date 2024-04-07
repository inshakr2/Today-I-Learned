package org.example.cache.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.cache.domain.entity.RedisHashUser;
import org.example.cache.domain.entity.User;
import org.example.cache.domain.repository.RedisHashUserRepository;
import org.example.cache.domain.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static org.example.cache.config.CacheConfig.CACHE1;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RedisHashUserRepository redisHashUserRepository;
    private final RedisTemplate<String, User> userRedisTemplate;
    private final RedisTemplate<String, Object> objectRedisTemplate;

    public User getUser(final Long id) {
        // 1. cache get
        String key = "users:%d".formatted(id);
        User cachedUser = (User) objectRedisTemplate.opsForValue().get(key);

        if (cachedUser != null) {
            return cachedUser;
        }

        // 2. else db -> cache set
        User user = userRepository.findById(id).orElseThrow();
        objectRedisTemplate.opsForValue().set(key, user, Duration.ofSeconds(30));

        return user;
    }

    public RedisHashUser getHashUser(Long id) {
        RedisHashUser cachedUser = redisHashUserRepository.findById(id).orElseGet(() -> {
            User user = userRepository.findById(id).orElseThrow();
            return redisHashUserRepository.save(RedisHashUser.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .createdAt(user.getCreatedAt())
                    .updatedAt(user.getUpdatedAt())
                    .build());
        });

        return cachedUser;
    }

    @Cacheable(cacheNames = CACHE1, key = "'user:' + #id")
    public User getCacheableUser(final Long id) {
        return  userRepository.findById(id).orElseThrow();
    }
}
