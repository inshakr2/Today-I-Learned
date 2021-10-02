package chany.board2.domain;

import chany.board2.dto.BoardDto;
import chany.board2.dto.BoardResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity @Getter @Setter(PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Board {

    @Id @GeneratedValue
    private Long id;
    @Column(length = 10, nullable = false)
    private String author;
    @Column(length = 100, nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public static Board createBoard(BoardDto boardDto) {

        Board board = new Board();
        board.setAuthor(boardDto.getAuthor());
        board.setContent(boardDto.getContent());
        board.setTitle(boardDto.getTitle());

        return board;
    }

    public void updateBoard(BoardResponseDto boardDto) {
        this.author = boardDto.getAuthor();
        this.content = boardDto.getContent();
        this.title = boardDto.getTitle();
    }
}
