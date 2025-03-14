package com.example.comment.service.api;

import com.example.comment.service.response.CommentPageResponse;
import com.example.comment.service.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class CommentApiV2Test {

    RestClient restClient = RestClient.create("http://localhost:9001");

    @Test
    void create() {
        CommentResponse comment1 = createCommentV2(new CommentCreateRequestV2(1L, "comment1", null, 1L));
        CommentResponse comment2 = createCommentV2(new CommentCreateRequestV2(1L, "comment2", comment1.getPath(), 1L));
        CommentResponse comment3 = createCommentV2(new CommentCreateRequestV2(1L, "comment3", comment2.getPath(), 1L));

        System.out.println("comment1.getCommentId() = " + comment1.getCommentId());
        System.out.println("\tcomment2.getCommentId() = " + comment2.getCommentId());
        System.out.println("\tcomment3.getCommentId() = " + comment3.getCommentId());
//        comment1.getCommentId() = 152747412450836480
//          comment2.getCommentId() = 152747413453275136
//          comment3.getCommentId() = 152747413507801088
    }

    CommentResponse createCommentV2(CommentCreateRequestV2 request) {
        return restClient.post()
                .uri("/v2/comments")
                .body(request)
                .retrieve()
                .body(CommentResponse.class);
    }

    @Test
    void read() {
        CommentResponse response = restClient.get()
                .uri("/v2/comments/{comment_id}", 152747412450836480L)
                .retrieve()
                .body(CommentResponse.class);

        System.out.println("response = " + response);
    }

    @Test
    void delete() {
//        comment1.getCommentId() = 152747412450836480
//          comment2.getCommentId() = 152747413453275136
//          comment3.getCommentId() = 152747413507801088
        restClient.delete()
                .uri("/v2/comments/{comment_id}", 152747413507801088L)
                .retrieve();
    }

    @Test
    void readAll() {
        CommentPageResponse response = restClient.get()
                .uri("/v2/comments?articleId=1&page=50000&pageSize=10")
                .retrieve()
                .body(CommentPageResponse.class);

        System.out.println("response.getCommentCount() = " + response.getCommentCount());
        for (CommentResponse comment : response.getComments()) {
            System.out.println("comment.getCommentId() = " + comment.getCommentId());
        }
        /**
         * response.getCommentCount() = 101
         * comment.getCommentId() = 152753497621868544
         * comment.getCommentId() = 152753497659617281
         * comment.getCommentId() = 152753497659617295
         * comment.getCommentId() = 152753497659617301
         * comment.getCommentId() = 152753497659617305
         * comment.getCommentId() = 152753497659617314
         * comment.getCommentId() = 152753497659617319
         * comment.getCommentId() = 152753497659617322
         * comment.getCommentId() = 152753497659617329
         * comment.getCommentId() = 152753497659617335
         */
    }

    @Test
    void readAllInfiniteScroll() {
        List<CommentResponse> response1 = restClient.get()
                .uri("/v2/comments/infinite-scroll?articleId=1&pageSize=10")
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponse>>() {
                });

        System.out.println("First Page");
        for (CommentResponse commentResponse : response1) {
            System.out.println("commentResponse.getCommentId() = " + commentResponse.getCommentId());
        }

        String lastPath = response1.getLast().getPath();

        List<CommentResponse> response2 = restClient.get()
                .uri("/v2/comments/infinite-scroll?articleId=1&pageSize=10&lastPath="+lastPath )
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponse>>() {
                });

        System.out.println("Second Page");
        for (CommentResponse commentResponse : response2) {
            System.out.println("commentResponse.getCommentId() = " + commentResponse.getCommentId());
        }
    }

    @Test
    void countTest() {
        CommentResponse comment1 = createCommentV2(new CommentCreateRequestV2(2L, "comment1", null, 1L));

        System.out.println("Create");
        Long count1 = restClient.get()
                .uri("/v2/comments/articles/{articleId}/count", 2L)
                .retrieve()
                .body(Long.class);
        System.out.println("count1 = " + count1);

        System.out.println("Delete");
        restClient.delete()
                .uri("/v2/comments/{comment_id}", comment1.getCommentId())
                .retrieve();

        Long count2 = restClient.get()
                .uri("/v2/comments/articles/{articleId}/count", 2L)
                .retrieve()
                .body(Long.class);
        System.out.println("count2 = " + count2);
    }

    @Getter
    @AllArgsConstructor
    public static class CommentCreateRequestV2 {
        private Long articleId;
        private String content;
        private String parentPath;
        private Long writerId;
    }
}
