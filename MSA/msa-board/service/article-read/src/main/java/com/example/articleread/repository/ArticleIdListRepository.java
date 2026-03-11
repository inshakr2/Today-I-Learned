package com.example.articleread.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.Limit;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArticleIdListRepository {
    private final StringRedisTemplate redisTemplate;

    // article-read::board::{boardId}::article-list
    private static final String KEY_FORMAT = "article-read::board::%s::article-list";

    public void add(Long boardId, Long articleId, Long limit) {
        redisTemplate.executePipelined((RedisCallback<?>) action -> {
            StringRedisConnection conn = (StringRedisConnection) action;
            String key = generateKey(boardId);
            // score 가 Double 형이므로 스코어를 통일하고 value 값을 snowflake 값으로 사용
            // snowflake 아이디는 오름차순으로 생성되고, 0으로 패딩하므로 zset에서도 사전순 정렬이 보장됨.
            conn.zAdd(key, 0, toPaddedString(articleId));
            // 오름차순 정렬 결과에서 뒤쪽 limit개를 제외한 나머지를 삭제한다. 즉 상위 데이터를 limit 개수까지만 살림.
            conn.zRemRange(key, 0, - limit - 1);
            return null;
        });
    }

    public void delete(Long boardId, Long articleId) {
        redisTemplate.opsForZSet().remove(generateKey(boardId), toPaddedString(articleId));
    }

    public List<Long> readAll(Long boardId, Long offset, Long limit) {
        return redisTemplate.opsForZSet()
                .reverseRange(generateKey(boardId), offset, offset + limit - 1)
                .stream().map(Long::valueOf).toList();
    }

    public List<Long> readAllInfiniteScroll(Long boardId, Long lastArticleId, Long limit) {
        return redisTemplate.opsForZSet().reverseRangeByLex( // 사전순 내림차순 정렬
                generateKey(boardId),
                // 제한없이 내림차순 limit개 조회 : articleId < lastArticleId 인 데이터만 조회 (exclusive로 미만 옵션 명시)
                lastArticleId == null ? Range.unbounded() : Range.leftUnbounded(Range.Bound.exclusive(toPaddedString(lastArticleId))),
                Limit.limit().count(limit.intValue())
        ).stream().map(Long::valueOf).toList();
    }

    private String toPaddedString(Long articleId) {
        return "%019d".formatted(articleId);
    }

    public String generateKey(Long boardId) {
        return KEY_FORMAT.formatted(boardId);
    }
}
