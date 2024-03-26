package com.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

public class ListType {

    public static void main(String[] args) {
        try (JedisPool jedisPool = new JedisPool("localhost", 6379)) {
            try (Jedis jedis = jedisPool.getResource()) {

                String key = "queue";

//                for (int i = 0; i <= 5; i++) {
//                    jedis.rpush(key, Integer.toString(i));
//                }

//                List<String> lrange = jedis.lrange(key, 0, -1);
//                lrange.forEach(System.out::println);

//                System.out.println(jedis.rpop(key));
//                System.out.println(jedis.rpop(key));
//                System.out.println(jedis.rpop(key));
//                System.out.println(jedis.rpop(key));
//                System.out.println(jedis.rpop(key));

                while (true) {
                    List<String> blpop = jedis.blpop(30, "queue:blocking");
                    if (blpop != null) {
                        blpop.forEach(System.out::println);
                    }
                }
            }
        }
    }
}
