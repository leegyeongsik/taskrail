package com.taskrail.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleUserInfoDto {

    private String id;
    private String email;

    public GoogleUserInfoDto(String id, String email){
        this.id = id;
        this.email = email;
    }
}
