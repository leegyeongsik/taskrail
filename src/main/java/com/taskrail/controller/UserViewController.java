package com.taskrail.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.taskrail.jwt.JwtUtil;
import com.taskrail.service.KakaoService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserViewController {

    private final KakaoService kakaoService;

    // 사용자 로그인 페이지
    @GetMapping("/login")
    public String loginPage() {
        return "login-page";
    }

    // 사용자 정보 수정 페이지
    @GetMapping("/profile")
    public String getProfilePage() {
        return "profile-page";
    }


    // 카카오 서버에서 보내주는 코드를 @RequestParam 으로 받을 예정입니다.
    @GetMapping("/api/users/kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        String token = kakaoService.kakaoLogin(code); // 반환 값이 JWT 토큰

        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER,token.substring(7));
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/main";
    }

}