package com.taskrail.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


//카드에 할당인원 추가
@Getter
@Setter
public class CardAssignUserRequestDto {
    List<Long> user_id;

    public void add(Long userId){
        user_id.add(userId);
    }
}
