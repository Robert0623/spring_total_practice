package com.myportfolio.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegisterController {
    @RequestMapping("/register/add")
    public String register() {
        return "registerForm";
    }
}