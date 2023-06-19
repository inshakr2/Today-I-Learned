package chany.board.controller;

import chany.board.dto.BoardDto;
import chany.board.dto.BoardSearchCondition;
import chany.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public String search(@PageableDefault(value = 10, sort = "createdDate")Pageable pageable,
                       @ModelAttribute("condition") BoardSearchCondition condition,
                       Model model) {
        Page<BoardDto> search = boardService.getSearchList(condition, pageable);
        int totalPage = search.getTotalPages();

        model.addAttribute("postList", search.getContent());
        model.addAttribute("totalPage", totalPage);
        return "board/list.html";
    }

    @GetMapping("/post")
    public String post() {
        return "board/post.html";
    }

    @PostMapping("/post")
    public String write(BoardDto boardDto) {
        boardService.savePost(boardDto);
        return "redirect:/";
    }

    @GetMapping("/post/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        BoardDto boardDto = boardService.getPost(id);
        model.addAttribute("post", boardDto);

        return "board/detail.html";
    }

    @GetMapping("/post/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        BoardDto post = boardService.getPost(id);
        model.addAttribute("post", post);
        return "board/edit.html";
    }

    @PutMapping("/post/edit/{id}")
    public String update(BoardDto boardDto) {
        boardService.savePost(boardDto);
        return "redirect:/";
    }

    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.deletePost(id);
        return "redirect:/";
    }
}
