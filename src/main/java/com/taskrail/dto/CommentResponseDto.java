package com.taskrail.dto;

import com.taskrail.entity.Card;
import com.taskrail.entity.Comment;
import com.taskrail.entity.User;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private Long userId;
    private String userName;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userId = comment.getUser().getId();
        this.userName = comment.getUser().getName();
    }



}
