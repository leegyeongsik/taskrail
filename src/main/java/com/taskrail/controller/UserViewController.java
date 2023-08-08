package com.taskrail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/user")
public class UserViewController {

    // 사용자 로그인 페이지
    @GetMapping("/login-page")
    public String loginPage() {
        return "login-page";
    }

    // 사용자 정보 수정 페이지
    @GetMapping("/profile-page")
    public String getProfilePage() {
        return "profile-page";
    }

}