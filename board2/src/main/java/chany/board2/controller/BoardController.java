package chany.board2.controller;

import chany.board2.dto.BoardDto;
import chany.board2.dto.BoardResponseDto;
import chany.board2.dto.SearchCondition;
import chany.board2.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public String home(Model model,
                       @ModelAttribute("condition") SearchCondition condition,
                       @PageableDefault(value = 10, sort = "createdDate")Pageable pageable) {
        Page<BoardResponseDto> search = boardService.searchByCondition(condition, pageable);

        model.addAttribute("postList", search.getContent());
        model.addAttribute("totalPage", search.getTotalPages());
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

    @GetMapping("/post/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        BoardResponseDto boardDetail = boardService.getBoardDetail(id);
        model.addAttribute("post", boardDetail);

        return "board/detail.html";
    }

    @GetMapping("/post/edit/{id}")
    public String editPage(@PathVariable("id") Long id, Model model) {
        BoardResponseDto boardDetail = boardService.getBoardDetail(id);
        model.addAttribute("post", boardDetail);
        return "/board/edit.html";
    }

    @PatchMapping("/post/edit/{id}")
    public String edit(@PathVariable("id") Long id, BoardResponseDto boardResponseDto) {
        boardService.updatePost(id, boardResponseDto);
        return "redirect:/";
    }

    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.deletePost(id);
        return "redirect:/";
    }

}
