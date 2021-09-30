package chany.board2.controller;

import chany.board2.dto.BoardDto;
import chany.board2.dto.BoardResponseDto;
import chany.board2.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public String home(Model model) {
        List<BoardResponseDto> boardList = boardService.getBoardList();
        model.addAttribute("postList", boardList);
        return "board/home.html";
    }

    @GetMapping("/post")
    public String post() {
        return "board/post.html";
    }

    @PostMapping("/post")
    public String write(@Valid BoardDto boardDto) {
        boardService.savePost(boardDto);
        return "redirect:/";
    }

}
