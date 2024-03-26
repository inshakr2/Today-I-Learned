package com.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.resps.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortedSetType {

    public static void main(String[] args) {
        try (JedisPool jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try (Jedis jedis = jedisPool.getResource()) {
                String key = "game2:score";

                Map<String, Double> map = new HashMap<>();
                map.put("user1", 100.0);
                map.put("user2", 300.0);
                map.put("user3", 50.0);
                map.put("user4", 0.0);

                jedis.zadd(key, map);

                List<String> zrange = jedis.zrange(key, 0, Integer.MAX_VALUE);
                zrange.forEach(System.out::println);

                List<Tuple> tuples = jedis.zrangeWithScores(key, 0, Integer.MAX_VALUE);
                tuples.forEach(i -> System.out.printf("%s - %f\n".formatted(i.getElement(), i.getScore())));

                jedis.zincrby(key, 1000, "user4");
                List<Tuple> tuples2 = jedis.zrangeWithScores(key, 0, Integer.MAX_VALUE);
                tuples2.forEach(i -> System.out.printf("%s - %f\n".formatted(i.getElement(), i.getScore())));

            }
        }
    }
}
