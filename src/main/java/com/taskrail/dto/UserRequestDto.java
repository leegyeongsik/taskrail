package com.taskrail.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    // 회원 정보 수정 시에

    @NotBlank(message = "비밀번호 입력이 안되었습니다.")
    private String password;

    @NotBlank(message = "새 비밀번호 입력이 안되었습니다.")
    private String newPassword;

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

}
