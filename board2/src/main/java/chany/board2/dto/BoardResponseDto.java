package chany.board2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BoardResponseDto {

    private Long id;
    private String author;
    private String title;
    private String content;
    private LocalDateTime createdDate;

}
