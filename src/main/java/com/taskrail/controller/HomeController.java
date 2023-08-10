package com.taskrail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class HomeController {

    // 커버 페이지
    @GetMapping("/cover")
    public String CoverPage() {
        return "cover-page";
    }

    // 메인 페이지
    @GetMapping("/main")
    public String MainPage() {
        return "main-page";
    }

}
