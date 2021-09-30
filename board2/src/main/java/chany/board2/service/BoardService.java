package chany.board2.service;

import chany.board2.domain.Board;
import chany.board2.dto.BoardDto;
import chany.board2.dto.BoardResponseDto;
import chany.board2.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

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

}