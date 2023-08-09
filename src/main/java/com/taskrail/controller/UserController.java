package com.taskrail.controller;

import com.taskrail.dto.RestApiResponseDto;
import com.taskrail.dto.SignupRequestDto;
import com.taskrail.dto.UserRequestDto;
import com.taskrail.security.UserDetailsImpl;
import com.taskrail.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserServiceImpl userService;

    // 회원가입 API
    @PostMapping("/users/signup")
    public void signup(
            @Valid @RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
    }


    // 사용자 정보 수정 API
    @PutMapping("/users")
    public void updateUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody UserRequestDto requestDto) {
        userService.updateUser(requestDto,userDetails.getUser());
    }


    // 사용자 정보 삭제 API
    @DeleteMapping("/users")
    public void deleteUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody UserRequestDto requestDto) {
        userService.deleteUser(requestDto,userDetails.getUser());
    }


    // 회원 가입 시 Validation 예외 처리 -> defaultMessage 를 클라이언트 측에 반환합니다.
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<RestApiResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        RestApiResponseDto restApiException = new RestApiResponseDto(HttpStatus.BAD_REQUEST.value(),ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        // 위 문장에 breakCheck 하고 디버깅 하면 defaultMessage 위치를 알 수 있음.
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

}
