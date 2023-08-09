package com.taskrail.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CardRequestDto {
    private String title;
    private String content;
    private String color;
    private LocalDateTime due_date;

}
