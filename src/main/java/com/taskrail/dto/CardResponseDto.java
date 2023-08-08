package com.taskrail.dto;

import com.taskrail.entity.Card;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String color;
    private Long orders;
    private LocalDateTime due_Date;


    public CardResponseDto(Card card) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.content = card.getContent();
        this.color = card.getColor();
        this.orders = card.getOrders();
        this.due_Date = card.getDue_date();
    }
}
