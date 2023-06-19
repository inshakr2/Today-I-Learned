package chany.board2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {

    @NotEmpty
    private String author;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
}
