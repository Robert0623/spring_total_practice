package com.myportfolio.web.controller;

import com.myportfolio.web.domain.BoardDto;
import com.myportfolio.web.domain.PageHandler;
import com.myportfolio.web.domain.SearchCondition;
import com.myportfolio.web.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardService;

    @PostMapping("/modify")
    public String modify(BoardDto boardDto, Integer page, Integer pageSize, Model m, HttpSession session, RedirectAttributes rattr) {
        String writer = (String) session.getAttribute("id");
        boardDto.setWriter(writer);
        try {
            int rowCnt = boardService.modify(boardDto);

            if(rowCnt!=1)
                throw new Exception("Modify Failed");

            rattr.addAttribute("page", page);
            rattr.addAttribute("pageSize", pageSize);
            rattr.addFlashAttribute("msg", "MOD_OK");

            return "redirect:/board/list";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute(boardDto);
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);
            m.addAttribute("msg", "MOD_ERR");
            return "board";
        }
    }

    @PostMapping("/write")
    public String write(BoardDto boardDto, Model m, HttpSession session, RedirectAttributes rattr) {
        String writer = (String) session.getAttribute("id");
        boardDto.setWriter(writer);
        try {
            int rowCnt = boardService.write(boardDto);

            if(rowCnt!=1)
                throw new Exception("Write Failed");

            rattr.addFlashAttribute("msg", "WRT_OK");

            return "redirect:/board/list";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("mode", "new");
            m.addAttribute(boardDto);
            m.addAttribute("msg", "WRT_ERR");
            return "board";
        }
    }

    @GetMapping("/write")
    public String write(Model m) {
        m.addAttribute("mode", "new");
        return "board"; //읽기와 쓰기에 사용. 쓰기에 사용할 때는 mode=new
    }

    @PostMapping("/remove")
    public String remove(Integer bno, Integer page, Integer pageSize, HttpSession session, RedirectAttributes rattr) {
        String writer = (String) session.getAttribute("id");
        String msg = "DEL_OK";

        try {
            int rowCnt = boardService.remove(bno, writer);

            if(rowCnt!=1)
                throw new Exception("Remove Failed");

        } catch (Exception e) {
            e.printStackTrace();
            msg = "DEL_ERR";
        }
        rattr.addAttribute("page", page);
        rattr.addAttribute("pageSize", pageSize);
        rattr.addFlashAttribute("msg", msg);

        return "redirect:/board/list";
    }

    @GetMapping("/read")
    public String read(Integer bno, Integer page, Integer pageSize, Model m) {
        try {
            BoardDto boardDto = boardService.read(bno);
//            m.addAttribute("boardDto", boardDto); //아래 문장과 동일
            m.addAttribute(boardDto);
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "board";
    }

    @GetMapping("/list")
    public String list(SearchCondition sc, Model m, HttpServletRequest request) {
        if(!loginCheck(request))
            return "redirect:/login/login?toURL="+request.getRequestURL(); //로그인을 안했으면, 로그인 페이지로 이동

        try {

            int totalCnt = boardService.getSearchResultCnt(sc);
            m.addAttribute("totalCnt", totalCnt);

            PageHandler pageHandler = new PageHandler(totalCnt, sc);

            List<BoardDto> list = boardService.getSearchResultPage(sc);
            m.addAttribute("list", list);
            m.addAttribute("ph", pageHandler);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "boardList"; //로그인을 한 상태면, 게시판으로 이동
    }

    private boolean loginCheck(HttpServletRequest request) {
        //1. 세션을 얻는다.
        HttpSession session = request.getSession();
        //2-1. 세션에 id가 있는지 확인한다.
        //2-2. id가 있으면 true, 없으면 false를 반환.
//        if(session.getAttribute("id")!=null)
//            return true;
//        else
//            return false;
        return session.getAttribute("id")!=null;

    }
}
