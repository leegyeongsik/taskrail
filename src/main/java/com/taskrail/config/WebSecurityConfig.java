package com.taskrail.config;

import org.springframework.web.cors.CorsConfiguration; // 꼭 체크!!
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // 꼭 체크!!
import org.springframework.web.filter.CorsFilter; // 꼭 체크!!
import com.taskrail.jwt.JwtUtil;
import com.taskrail.security.JwtAuthenticationFilter;
import com.taskrail.security.JwtAuthorizationFilter;
import com.taskrail.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManger(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
        filter.setAuthenticationManager(authenticationManger(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf((csrf) -> csrf.disable());

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // 토큰이 없어도 접근가능하도록
        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 접근 허용 설정
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/main").permitAll() // 메인 페이지 요청 허가
                        .requestMatchers("/cover").permitAll() // 커버 페이지 요청 허가
                        .requestMatchers("/login").permitAll() // 로그인, 회원가입 페이지 요청 허가
                        .requestMatchers("/profile").permitAll() // 사용자 정보 수정,탈퇴 페이지 요청 허가 -> 클라이언트 측에서 거부함.
                        .requestMatchers("/api/users/**").permitAll() // 회원가입, 로그인으로 시작하는 요청 모두 접근 허가
                        //.requestMatchers(HttpMethod.GET,"/api/post/**").permitAll() // 선택 게시글 조회
                        //.requestMatchers("/api/post/**").authenticated()
                        .anyRequest().authenticated() // 그 외 모든 요청 인증처리
        );



        // 필터 관리
        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
