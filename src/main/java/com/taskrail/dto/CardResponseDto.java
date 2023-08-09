package com.taskrail.dto;

import com.taskrail.entity.Card;
import com.taskrail.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class CardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String color;
    private Long orders;
    private LocalDateTime due_Date;
    private List<CommentResponseDto> commentList;


    public CardResponseDto(Card card) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.content = card.getContent();
        this.color = card.getColor();
        this.orders = card.getOrders();
        this.due_Date = card.getDue_date();
        this.commentList = card.getCommentList().stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }


}
