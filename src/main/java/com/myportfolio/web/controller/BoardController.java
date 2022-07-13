package com.myportfolio.web.controller;

import com.myportfolio.web.domain.BoardDto;
import com.myportfolio.web.domain.PageHandler;
import com.myportfolio.web.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/list")
    public String list(Integer page, Integer pageSize, Model m, HttpServletRequest request) {
        if(!loginCheck(request))
            return "redirect:/login/login?toURL="+request.getRequestURL(); //로그인을 안했으면, 로그인 페이지로 이동

        if(page==null) page = 1;
        if(pageSize==null) pageSize = 10;

        try {

            int totalCnt = boardService.getCount();
            PageHandler pageHandler = new PageHandler(totalCnt, page, pageSize);

            Map map = new HashMap();
            map.put("offset", (page-1)*pageSize);
            map.put("pageSize", pageSize);

            List<BoardDto> list = boardService.getPage(map);
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
