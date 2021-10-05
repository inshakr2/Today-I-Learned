package chany.board2.service;

import chany.board2.domain.Board;
import chany.board2.dto.BoardDto;
import chany.board2.dto.BoardResponseDto;
import chany.board2.dto.SearchCondition;
import chany.board2.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Board savePost(BoardDto boardDto) {
        Board board = boardRepository.save(Board.createBoard(boardDto));

        return board;
    }

    public List<BoardResponseDto> getBoardList() {
        List<Board> boards = boardRepository.findAll();
        List<BoardResponseDto> dtoList = new ArrayList<>();

        for (Board board : boards) {
            dtoList.add(new BoardResponseDto(board.getId(), board.getAuthor(),
                    board.getTitle(), board.getContent(), board.getCreatedDate()));
        }

        return dtoList;
    }

    public List<BoardResponseDto> searchByCondition(SearchCondition condition) {
        return boardRepository.searchBoardByCondition(condition);

    }

    public BoardResponseDto getBoardDetail(Long id) {
        Board board = boardRepository.findById(id).orElse(null);
        BoardResponseDto detail = new BoardResponseDto(board.getId(), board.getAuthor(),
                board.getTitle(), board.getContent(), board.getCreatedDate());

        return detail;
    }

    @Transactional
    public Board updatePost(Long id, BoardResponseDto boardResponseDto) {
        Board board = boardRepository.findById(id).orElse(null);
        board.updateBoard(boardResponseDto);
        boardRepository.save(board);
        return board;
    }

    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }

}
