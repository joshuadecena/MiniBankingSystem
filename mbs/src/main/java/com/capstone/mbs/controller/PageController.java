package com.capstone.mbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")
    public String login() {
        // forward to static login page located at src/main/resources/static/login/login.html
        return "forward:/app/components/login.html";
    }
}
