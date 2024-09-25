package org.suhodo.sb_ex02.controller.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.suhodo.sb_ex02.dto.*;
import org.suhodo.sb_ex02.service.BoardService;
import org.suhodo.sb_ex02.dto.BoardDTO;
import org.suhodo.sb_ex02.dto.BoardListAllDTO;
import org.suhodo.sb_ex02.dto.PageRequestDTO;
import org.suhodo.sb_ex02.dto.PageResponseDTO;

import javax.validation.Valid;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // /board/list
    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        //PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);

        //PageResponseDTO<BoardListReplyCountDTO> responseDTO = boardService.listWithReplyCount(pageRequestDTO);

        PageResponseDTO<BoardListAllDTO> responseDTO = boardService.listWithAll(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);
    }

    @GetMapping("/register")
    public void registerGET(){

    }

    @PostMapping("/register")
    public String registerPost(@Valid BoardDTO boardDTO, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){
        log.info("board POST register.....");

        // @Valid처리를 통해 BoardDTO의 제약사항에 위배되면
        // 아래 에러가 발생한다.
        if(bindingResult.hasErrors()){
            log.info("has errors..........");
            // redirect전송 시 처음 1번 "errors"값을 꺼내도록 전송한다.
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/board/register";
        }

        log.info(boardDTO);

        Long bno = boardService.register(boardDTO);
        redirectAttributes.addFlashAttribute("result", bno);

        return "redirect:/board/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model){
        BoardDTO boardDTO = boardService.readOne(bno);

        log.info(boardDTO);

        model.addAttribute("dto", boardDTO);
    }

    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO,
                         @Valid BoardDTO boardDTO,
                         BindingResult bindingResult, RedirectAttributes redirectAttributes){
        log.info("board modify post............." + boardDTO);

        if(bindingResult.hasErrors()){
            log.info("has errors.............");

            String link = pageRequestDTO.getLink();

            // redirect시 1회성으로 꺼내는 값
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            // redirect시 상시 꺼낼 수 있는 값
            redirectAttributes.addAttribute("bno", boardDTO.getBno());

            return "redirect:/board/modify?" + link;
        }

        boardService.modify(boardDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("bno", boardDTO.getBno());

        return "redirect:/board/read";
    }

    @PostMapping("/remove")
    public String remove(BoardDTO boardDTO, RedirectAttributes redirectAttributes){
        Long bno = boardDTO.getBno();

        log.info("remove post..." + bno);

        boardService.remove(bno);


        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/board/list";
    }
}