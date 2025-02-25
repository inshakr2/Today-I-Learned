package com.example.comment.service.api;

import com.example.comment.service.response.CommentPageResponse;
import com.example.comment.service.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class CommentApiTest {

    RestClient restClient = RestClient.create("http://localhost:9001");

    @Test
    void create() {
        CommentResponse comment1 = createComment(new CommentCreateRequest(1L, "comment1", null, 1L));
        CommentResponse comment2 = createComment(new CommentCreateRequest(1L, "comment2", comment1.getCommentId(), 1L));
        CommentResponse comment3 = createComment(new CommentCreateRequest(1L, "comment3", comment1.getCommentId(), 1L));

        System.out.println("comment1.getCommentId() = " + comment1.getCommentId());
        System.out.println("\tcomment2.getCommentId() = " + comment2.getCommentId());
        System.out.println("\tcomment3.getCommentId() = " + comment3.getCommentId());
//        comment1.getCommentId() = 150935945368592384
//          comment2.getCommentId() = 150935945913851904
//          comment3.getCommentId() = 150935945976766464
    }

    CommentResponse createComment(CommentCreateRequest request) {
        return restClient.post()
                .uri("/v1/comments")
                .body(request)
                .retrieve()
                .body(CommentResponse.class);
    }

    @Test
    void read() {
        CommentResponse response = restClient.get()
                .uri("/v1/comments/{comment_id}", 150935945368592384L)
                .retrieve()
                .body(CommentResponse.class);

        System.out.println("response = " + response);
    }

    @Test
    void delete() {
//        comment1.getCommentId() = 150935945368592384
//          comment2.getCommentId() = 150935945913851904
//          comment3.getCommentId() = 150935945976766464
        restClient.delete()
                .uri("/v1/comments/{comment_id}", 150935945976766464L)
                .retrieve();
    }

    @Test
    void readAll() {
        CommentPageResponse response = restClient.get()
                .uri("/v1/comments?articleId=1&page=1&pageSize=10")
                .retrieve()
                .body(CommentPageResponse.class);

        System.out.println("response.getCommentCount() = " + response.getCommentCount());
        for (CommentResponse comment : response.getComments()) {
            if (!comment.getCommentId().equals(comment.getParentCommentId())) {
                System.out.print("\t");
            }
            System.out.println("comment.getCommentId() = " + comment.getCommentId());
        }
        /**
         * 1 page
         response.getCommentCount() = 101
         comment.getCommentId() = 150940079815229440
         comment.getCommentId() = 150940079844589574
         comment.getCommentId() = 150940079815229441
         comment.getCommentId() = 150940079844589568
         comment.getCommentId() = 150940079815229442
         comment.getCommentId() = 150940079844589577
         comment.getCommentId() = 150940079815229443
         comment.getCommentId() = 150940079844589572
         comment.getCommentId() = 150940079815229444
         comment.getCommentId() = 150940079844589573
         */
    }

    @Test
    void readAllInfiniteScroll() {
        List<CommentResponse> response1 = restClient.get()
                .uri("/v1/comments/infinite-scroll?articleId=1&pageSize=5")
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponse>>() {
                });

        System.out.println("First Page");
        for (CommentResponse comment : response1) {
            if (!comment.getCommentId().equals(comment.getParentCommentId())) {
                System.out.print("\t");
            }
            System.out.println("comment.getCommentId() = " + comment.getCommentId());
        }

        Long lastParentCommentId = response1.getLast().getParentCommentId();
        Long lastCommentId = response1.getLast().getCommentId();

        List<CommentResponse> response2 = restClient.get()
                .uri("/v1/comments/infinite-scroll?articleId=1&pageSize=5&lastParentCommentId=%s&lastCommentId=%s"
                        .formatted(lastParentCommentId, lastCommentId))
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponse>>() {
                });

        System.out.println("Second Page");
        for (CommentResponse comment : response2) {
            if (!comment.getCommentId().equals(comment.getParentCommentId())) {
                System.out.print("\t");
            }
            System.out.println("comment.getCommentId() = " + comment.getCommentId());
        }

        /**
         First Page
         comment.getCommentId() = 150940079815229440
         comment.getCommentId() = 150940079844589574
         comment.getCommentId() = 150940079815229441
         comment.getCommentId() = 150940079844589568
         comment.getCommentId() = 150940079815229442
         Second Page
         comment.getCommentId() = 150940079844589577
         comment.getCommentId() = 150940079815229443
         comment.getCommentId() = 150940079844589572
         comment.getCommentId() = 150940079815229444
         comment.getCommentId() = 150940079844589573
         */
    }

    @Getter
    @AllArgsConstructor
    public static class CommentCreateRequest {
        private Long articleId;
        private String content;
        private Long parentCommentId;
        private Long writerId;
    }
}
