package com.example.comment.repository;

import com.example.comment.entity.CommentV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryV2 extends JpaRepository<CommentV2, Long> {

    @Query("select c from CommentV2 c where c.commentPath.path = :path")
    Optional<CommentV2> findByPath(@Param("path") String path);

    @Query(
            value = "SELECT path From comment_v2 " +
                    "WHERE article_id = :articleId " +
                    "   AND path > :pathPrefix " +
                    "   AND path like :pathPrefix% " +
                    "ORDER BY path desc limit 1",
            nativeQuery = true)
    Optional<String> findDescendantsTopPath(
            @Param("articleId") Long articleId,
            @Param("pathPrefix") String pathPrefix
    );

    @Query(
            value = "SELECT comment_v2.comment_id, comment_v2.content, comment_v2.path, comment_v2.article_id, " +
                    "comment_v2.writer_id, comment_v2.deleted, comment_v2.created_at " +
                    "FROM (" +
                    "   SELECT comment_id FROM comment_v2 WHERE article_id = :articleId " +
                    "   ORDER BY path asc " +
                    "   limit :limit offset :offset " +
                    ") t LEFT JOIN comment_v2 ON t.comment_id = comment_v2.comment_id ",
            nativeQuery = true
    )
    List<CommentV2> findAll(
            @Param("articleId") Long articleId,
            @Param("offset") Long offset,
            @Param("limit") Long limit
    );

    @Query(
            value = "SELECT count(*) FROM (" +
                    "   SELECT comment_id FROM comment_v2 WHERE article_id = :articleId limit :limit" +
                    ") t",
            nativeQuery = true
    )
    Long count(
            @Param("articleId") Long articleId,
            @Param("limit") Long limit
    );

    @Query(
            value = "SELECT comment_v2.comment_id, comment_v2.content, comment_v2.path, comment_v2.article_id, " +
                    "comment_v2.writer_id, comment_v2.deleted, comment_v2.created_at " +
                    "FROM comment_v2 " +
                    "WHERE article_id = :articleId " +
                    "ORDER BY path asc limit :limit" ,
            nativeQuery = true
    )
    List<CommentV2> findAllInfiniteScroll(
            @Param("articleId") Long articleId,
            @Param("limit") Long limit
    );


    @Query(
            value = "SELECT comment_v2.comment_id, comment_v2.content, comment_v2.path, comment_v2.article_id, " +
                    "comment_v2.writer_id, comment_v2.deleted, comment_v2.created_at " +
                    "FROM comment_v2 " +
                    "WHERE article_id = :articleId AND path > :lastPath " +
                    "ORDER BY path asc " +
                    "limit :limit" ,
            nativeQuery = true
    )
    List<CommentV2> findAllInfiniteScroll(
            @Param("articleId") Long articleId,
            @Param("lastPath") String lastPath,
            @Param("limit") Long limit
    );
}
