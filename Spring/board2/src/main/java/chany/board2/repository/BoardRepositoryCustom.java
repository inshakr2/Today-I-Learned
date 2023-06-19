package chany.board2.repository;

import chany.board2.dto.BoardResponseDto;
import chany.board2.dto.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardRepositoryCustom {

    Page<BoardResponseDto> searchBoardByCondition(SearchCondition condition, Pageable pageable);
}
