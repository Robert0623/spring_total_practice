package com.myportfolio.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("/login/login")
    public String login() {
        return "loginForm";
    }
}
