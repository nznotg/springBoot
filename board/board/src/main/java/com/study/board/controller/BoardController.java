package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    //글쓰기 페이지
    @GetMapping("/board/write")
    public String main() {
        return "boardWrite";
    }

    //글쓰기 로직 : DB에 insert
    @PostMapping("/board/writepro")
    public String boardWritePro(Board board){
        boardService.write(board);
        return "";
    }

    //DB data list 가져오기
    @GetMapping("/board/boardList")
    public String boardList(Model m) {
        m.addAttribute("list", boardService.boardList());
        return "boardList";
    }

    //특정 id의 게시글 상세보기 페이지 로직 처리
    @GetMapping("/board/view")
    public String boardView(Model m, Integer id) {
        m.addAttribute("board", boardService.boardView(id));
        return "boardView";
    }

    //특정 id의 게시글 삭제
    @GetMapping("/board/delete")
    public String boardDelete(Integer id){
        boardService.boardDelete(id);
        return "redirect:/board/boardList";
    }

    // 수정 페이지 구현
    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id
                                , Model m) {
        m.addAttribute("board", boardService.boardView(id));
        return "boardModify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board) {
        Board boardTemp = boardService.boardView(id); // boardTemp 에 id에 해당하는 DATA 담기
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());
        //parameter 로 넘어온 기존의 title / content를 boardTemp에 담아준 후 boardWritePro() 를 통해 다시 insert 해준다.
        boardService.write(boardTemp);
        // 그 후 List 페이지로 이동시킴
        return "redirect:/board/boardList";

    }
}
