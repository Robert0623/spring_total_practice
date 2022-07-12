package com.myportfolio.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping("/login")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        //1. 세션을 종료
        session.invalidate();
        //2. 홈으로 이동
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(String id, String pwd, String toURL, boolean rememberId, HttpSession session, HttpServletResponse response) throws Exception {
        //1. id와 pwd를 확인
        //2-1.  일치하지 않으면, loginForm으로 이동
        if (!loginCheck(id, pwd)) {
            String msg = URLEncoder.encode("id 또는 pwd가 일치하지 않습니다.", "utf-8");
            return "redirect:/login/login?msg=" + msg;
        }
        //2-2. id와 pwd가 일치하면,
        //====board에 들어갈 때 세션에 id가 있는지 체크하기 위해서 로그인 할 때 id를 저장====
        //request.getSession(); //이렇게 안하고 아래처럼 바로 session에 저장 가능.
        //세션 객체에 id를 저장
        session.setAttribute("id", id);

        //====아이디 체크 내용====
        //rememberId가 true면
        if(rememberId) {
            //1) 쿠키를 생성
            Cookie cookie = new Cookie("id", id);
            //2) 응답에 저장
            response.addCookie(cookie);

        //rememberId가 false면
        } else {
            //쿠키를 삭제해서 응답에 추가
            Cookie cookie = new Cookie("id", id);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        //3) 홈 또는 toURL로 이동
        toURL = toURL==null || toURL.equals("") ? "/" : toURL;
        return "redirect:"+toURL;
    }


    private boolean loginCheck(String id, String pwd) {
        return "asdf".equals(id) && "1234".equals(pwd);
    }
}


