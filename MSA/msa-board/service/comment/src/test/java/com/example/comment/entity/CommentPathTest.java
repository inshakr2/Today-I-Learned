package com.example.comment.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CommentPathTest {

    @Test
    void createChildCommentTest() {
        // 00000 <- 생성
        createChildCommentTest(CommentPath.create(""), null, "00000");

        // 00000
        //      00000 <- 생성
        createChildCommentTest(CommentPath.create("00000"), null, "0000000000");

        // 00000
        // 00001 <- 생성
        createChildCommentTest(CommentPath.create(""), "00000", "00001");

        // 0000z
        //      abcdz
        //            zzzzz
        //                  zzzzz
        //      abce0 <- 생성
        createChildCommentTest(CommentPath.create("0000z"), "0000zabcdzzzzzzzzzzz", "0000zabce0");

        // 00a0z
        //      00000
        //      00001
        //      00002
        //              00000
        //      00003 <- 생성
        createChildCommentTest(CommentPath.create("0000z"), "00a0z0000200000", "00a0z00003");

        // 00a0z
        //      00000
        //      00001
        //      00002
        //      00003 <- 생성
        createChildCommentTest(CommentPath.create("0000z"), "00a0z00002", "00a0z00003");

    }

    void createChildCommentTest(CommentPath commentPath, String descendantsTopPath, String expectedChildPath) {
        CommentPath childCommentPath = commentPath.createChildCommentPath(descendantsTopPath);
        assertThat(childCommentPath.getPath()).isEqualTo(expectedChildPath);
    }

    @Test
    void createChildCommentPathIfMaxDepthTest() {
        assertThatThrownBy(() ->
                CommentPath.create("zzzzz".repeat(5)).createChildCommentPath(null)
        ).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void createChildCommentPathIfChunkOverflowTest() {
        // given
        CommentPath commentPath = CommentPath.create("");

        // when, then
        assertThatThrownBy(() -> commentPath.createChildCommentPath("zzzzz"))
                .isInstanceOf(IllegalStateException.class);
    }
}