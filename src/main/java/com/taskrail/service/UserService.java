package com.taskrail.service;

import com.taskrail.dto.RestApiResponseDto;
import com.taskrail.dto.SignupRequestDto;
import com.taskrail.dto.UserRequestDto;
import com.taskrail.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

  /**
   * 회원가입 메서드
   * @param requestDto 회원가입 요청 정보 데이터
   */
  void signup(SignupRequestDto requestDto);


  /**
   * 사용자 정보 수정 메서드
   * @param id         수정할 사용자 id 값
   * @param requestDto 수정할 칼럼들에 대한 데이터
   * @param user       수정 요청한 로그인한 사용자
   * //@return HTTPStatusCode 상태 코드
   */
  void updateUser(
      Long id, UserRequestDto requestDto, User user);


  /**
   * 사용자 정보 탈퇴 메서드
   * @param id 탈퇴할 사용자 id 값
   * @param user 탈퇴 요청한 로그인한 사욪자
   * //@return HTTPStatusCode 상태 코드
   */
  void deleteUser(Long id, User user);



}


