package com.example.articleread.api;

import com.example.articleread.service.response.ArticleReadResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class ArticleReadApiTest {
    RestClient restClient = RestClient.create("http://localhost:9005");

    @Test
    void readTest() {
        ArticleReadResponse response = restClient.get()
                .uri("/v1/articles/{articleId}", 147361745406480462L)
                .retrieve()
                .body(ArticleReadResponse.class);

        System.out.println("response = " + response);
    }
}
