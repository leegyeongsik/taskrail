package com.taskrail.dto;

import com.taskrail.entity.Board;
import com.taskrail.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    Long UserId;
    String name;
    String email;
    boolean isin;
    public UserResponseDto(User user){
        this.UserId = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public UserResponseDto(User user ,  boolean isin){
        this.UserId = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.isin = isin;
    }

}
