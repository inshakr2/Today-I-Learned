package org.example.jediscache;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

@Component
@EnableAutoConfiguration(exclude = {
        JmxAutoConfiguration.class
})
public class RedisConfig {

    @Bean
    public JedisPool createJedisPool() {
        return new JedisPool("localhost", 6379);
    }
}
