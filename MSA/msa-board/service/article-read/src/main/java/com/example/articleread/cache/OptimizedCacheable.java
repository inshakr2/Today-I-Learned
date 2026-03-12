package com.example.articleread.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OptimizedCacheable {
    String type(); // Cache를 어떤 메서드에 붙일 지 유니크하게 구분하기 위한 type
    long ttlSeconds();

}
