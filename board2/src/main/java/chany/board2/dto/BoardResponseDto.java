package chany.board2.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BoardResponseDto {

    private Long id;
    private String author;
    private String title;
    private String content;
    private LocalDateTime createdDate;

    @QueryProjection
    public BoardResponseDto(Long id, String author, String title, String content, LocalDateTime createdDate) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
    }
}
