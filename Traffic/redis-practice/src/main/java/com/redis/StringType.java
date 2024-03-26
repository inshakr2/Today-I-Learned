package com.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.List;

public class StringType {
    public static void main(String[] args) {


        try (var jedisPool = new JedisPool("localhost", 6379)) {
            try (Jedis jedis = jedisPool.getResource()) {

                System.out.println(jedis.ping());

                Pipeline pipelined = jedis.pipelined();

                pipelined.set("users:301:name", "ted");
                pipelined.set("users:301:email", "ted@test.com");
                pipelined.set("users:301:age", "20");
                pipelined.set("users:301:introduce", "Hello");

                List<Object> objects = pipelined.syncAndReturnAll();
                objects.forEach(i -> System.out.println(i.toString()));
//                List<String> mget = jedis.mget("users:300:name", "users:300:email", "users:300:age", "users:300:introduce");
//                mget.forEach(System.out::println);


            }
        }
    }
}
