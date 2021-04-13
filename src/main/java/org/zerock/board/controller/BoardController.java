package org.zerock.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/board/")
@Log4j2
@RequiredArgsConstructor
public class BoardController {
    
    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info(">> list - get - pageRequestDTO: " + pageRequestDTO);
        model.addAttribute("result", boardService.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register() {
        log.info(">> register - get");
    }

    @PostMapping("/register")
    public String registerPost(BoardDTO dto, RedirectAttributes ra) {
        log.info(">> register - post - dto: " + dto);

        Long bno = boardService.register(dto);
        log.info("bno: " + bno);
        ra.addFlashAttribute("msg", bno);

        return "redirect:/board/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long bno, Model model) {
        log.info(">> read/modify - get - bno: " + bno);
        BoardDTO boardDTO = boardService.get(bno);
        log.info("boardDTO: " + boardDTO);

        model.addAttribute("dto", boardDTO);
    }

    @PostMapping("/remove")
    public String remove(long bno, RedirectAttributes ra) {
        log.info(">> remove - post - bno: " + bno);
        boardService.removeWithReplies(bno);
        ra.addFlashAttribute("msg", bno);

        return "redirect:/board/list";
    }

    @PostMapping("/modify")
    public String modify(BoardDTO boardDTO, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes ra) {
        log.info(">> modify - post - dto: " + boardDTO);

        boardService.modify(boardDTO);

        ra.addAttribute("page", requestDTO.getPage());
        ra.addAttribute("type", requestDTO.getType());
        ra.addAttribute("keyword", requestDTO.getKeyword());

        ra.addAttribute("bno", boardDTO.getBno());

        return "redirect:/board/read";
    }
}
