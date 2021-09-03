package chany.board.repository;

import chany.board.dto.BoardDto;
import chany.board.dto.BoardSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {
    Page<BoardDto> search(BoardSearchCondition condition, Pageable pageable);
}
