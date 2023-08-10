package com.taskrail.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.taskrail.jwt.JwtUtil;
import com.taskrail.service.GoogleService;
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
public class APILoginController {

  private final KakaoService kakaoService;
  private final GoogleService googleService;

  // 카카오 서버에서 보내주는 코드를 @RequestParam 으로 받을 예정입니다.
  @GetMapping("/api/users/kakao/callback")
  public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
    String token = kakaoService.kakaoLogin(code); // 반환 값이 JWT 토큰

    Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER,token.substring(7));
    cookie.setPath("/");
    response.addCookie(cookie);

    return "redirect:/view/main";
  }

  @GetMapping("/api/users/google/callback")
  public String googleLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
    String token = googleService.googleLogin(code); // 반환 값이 JWT 토큰

    Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER,token.substring(7));
    cookie.setPath("/");
    response.addCookie(cookie);

    return "redirect:/view/main";
  }

}
