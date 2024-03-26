package com.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

public class HashType {

    public static void main(String[] args) {
        try (JedisPool jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try (Jedis jedis = jedisPool.getResource()) {

                String key = "users:2:info";

                HashMap<String, String> hash = new HashMap<>();

                hash.put("name", "yeol");
                hash.put("email", "yeol@test.com");
                hash.put("phone", "010-1111-2222");

                jedis.hset(key, hash);

                Map<String, String> map = jedis.hgetAll(key);
                System.out.println("### get All ###");
//                map.entrySet().forEach(m -> System.out.println(m.getKey() + " : " + m.getValue()));
                map.forEach((k, v) -> System.out.printf("%s : %s\n", k, v));

                map.entrySet().forEach(m -> jedis.hdel(key, m.getKey()));

                System.out.println("### get All After Delete###");
                Map<String, String> map2 = jedis.hgetAll(key);
                map2.entrySet().forEach(m -> System.out.println(m.getKey() + " : " + m.getValue()));

                jedis.hincrBy(key, "visit", 10);
            }
        }
    }
}
