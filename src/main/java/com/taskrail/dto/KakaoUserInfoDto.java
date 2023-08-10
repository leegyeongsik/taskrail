package com.taskrail.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfoDto {

    private Long id;
    private String name;
    private String email;

    public KakaoUserInfoDto(Long id, String emailId, String email){
        this.id = id;
        this.name = emailId;
        this.email = email;
    }
}
