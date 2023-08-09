package com.taskrail.service;

import com.taskrail.dto.RestApiResponseDto;
import com.taskrail.dto.SignupRequestDto;
import com.taskrail.dto.UserRequestDto;
import com.taskrail.entity.User;
import com.taskrail.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j(topic = "User Service")
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public void signup(SignupRequestDto requestDto) {
        // 중복 체크
        if(userRepository.existsByName(requestDto.getName())){
            log.error("중복된 사용자가 회원가입을 시도하였습니다.");
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // DB에 저장하기 이전에 데이터 전처리
        String inputName = requestDto.getName();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();

        // 해당 정보를 생성자 메서드로 User 객체 생성 후 DB 에 저장
        User user = new User(inputName, email, password);
        userRepository.save(user);

        log.info("회원가입에 성공하였습니다.");
    }



    @Transactional
    public void updateUser(UserRequestDto requestDto, User user) {
        User updateUser = userRepository.findUserById(user.getId());

        if(!passwordEncoder.matches(requestDto.getPassword(),updateUser.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀립니다.");
        }

        String newPassword = passwordEncoder.encode(requestDto.getNewPassword());
        String email = requestDto.getEmail();

        updateUser.update(email,newPassword);

        log.info("회원 정보 수정을 성공하였습니다.");
    }


    public void deleteUser(UserRequestDto requestDto, User user) {
        User deleteUser = userRepository.findUserById(user.getId());

        if(!passwordEncoder.matches(requestDto.getPassword(),deleteUser.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀립니다.");
        }

        userRepository.delete(userRepository.findUserById(user.getId()));
        log.info("회원 정보 삭제를 성공하였습니다.");
    }


    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<RestApiResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        RestApiResponseDto restApiException = new RestApiResponseDto(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }


}
