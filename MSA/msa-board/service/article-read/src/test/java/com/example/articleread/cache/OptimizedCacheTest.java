package com.example.articleread.cache;

import lombok.*;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class OptimizedCacheTest {

    @Test
    void parseDataTest() {
        parseDataTest("data", 10);
        parseDataTest(15L, 10);
        parseDataTest(123, 10);
        parseDataTest(true, 10);
        parseDataTest(new TestClass("TEST"), 10);
    }

    @Test
    void isExpiredTest() {
        assertThat(OptimizedCache.of("data", Duration.ofDays(-30)).isExpired()).isTrue();
        assertThat(OptimizedCache.of("data", Duration.ofDays(30)).isExpired()).isFalse();
    }


    void parseDataTest(Object data, long ttlSeconds) {
        // given
        OptimizedCache optimizedCache = OptimizedCache.of(data, Duration.ofSeconds(ttlSeconds));
        System.out.println("optimizeCache = " + optimizedCache);

        // when
        Object resolveData = optimizedCache.parseData(data.getClass());

        // then
        System.out.println("resolveData = " + resolveData);
        assertThat(resolveData).isEqualTo(data);
    }

    @Getter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    static class TestClass {
        String testData;
    }
}