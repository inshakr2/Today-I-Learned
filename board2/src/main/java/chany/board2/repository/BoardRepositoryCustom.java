package chany.board2.repository;

import chany.board2.dto.BoardResponseDto;
import chany.board2.dto.SearchCondition;

import java.util.List;

public interface BoardRepositoryCustom {

    List<BoardResponseDto> searchBoardByCondition(SearchCondition condition);
}
