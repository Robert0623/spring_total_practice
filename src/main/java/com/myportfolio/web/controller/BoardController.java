package com.myportfolio.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/board")
public class BoardController {
    @GetMapping("/list")
    public String list(HttpServletRequest request) {
        if(!loginCheck(request))
            return "redirect:/login/login?toURL="+request.getRequestURL(); //로그인을 안했으면, 로그인 페이지로 이동

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
