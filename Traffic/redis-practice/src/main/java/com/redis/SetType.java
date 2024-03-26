package com.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class SetType {

    public static void main(String[] args) {

        try (JedisPool jedisPool = new JedisPool("localhost", 6379)) {
            try (Jedis jedis = jedisPool.getResource()) {

                String key1 = "user:100:follower";
                String key2 = "user:200:follower";

                jedis.sadd(key1, "100", "110", "120", "130", "140", "150");
                jedis.sadd(key2, "100", "200", "300", "300", "200", "100", "150");

                jedis.srem(key2, "150");

                boolean sismember = jedis.sismember(key1, "100");
                System.out.println("sismember = " + sismember);
                Set<String> sinter = jedis.sinter(key1, key2);
                sinter.forEach(System.out::println);
            }
        }
    }
}
