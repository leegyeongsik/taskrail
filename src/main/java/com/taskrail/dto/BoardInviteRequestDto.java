package com.taskrail.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class BoardInviteRequestDto {
    List<Long> user_id;
}
