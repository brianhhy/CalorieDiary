package com.example.cal_dia_mem.board.controller;

import com.example.cal_dia_mem.board.dto.BoardDTO;
import com.example.cal_dia_mem.board.entity.BoardEntity;
import com.example.cal_dia_mem.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/list")
    public String boardList(Model model,@PageableDefault(page = 0,size=10,sort = "id",direction = Sort.Direction.DESC) Pageable pageable){
        Page<BoardEntity> list = boardService.boardList(pageable);

        int nowPage= list.getPageable().getPageNumber()+1;
        int startPage = Math.max(nowPage-4,1);
        int endPage = Math.min(nowPage+5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "/board/boardindex";
    }

    @GetMapping("/board/write")
    public String boardWriteForm()
    {
        return "/board/write";
    }
    @PostMapping("/board/writepro")
    public String boardWritePro(BoardDTO boardDTO, Model model){
        System.out.println(boardDTO.getTitle()+boardDTO.getContent());
        boardService.Write(boardDTO);
        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");
        return "/member/message";
    }


    @GetMapping("/board/view")  // localhost:8081/board/view?id=1
    public String boardView(Model model, Integer id){

        model.addAttribute("board",boardService.boardView(id));
        return "/board/boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id){
        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model){
        model.addAttribute("board",boardService.boardView(id));
        return "/board/modify";
    }
    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, BoardEntity boardEntity) {

        BoardEntity boardTemp=boardService.boardView(id);

        boardTemp.setTitle(boardEntity.getTitle());
        boardTemp.setContent(boardEntity.getContent());

        boardService.Modify(boardTemp);

        return "redirect:/board/list";

    }
}
