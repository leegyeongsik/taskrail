package com.taskrail.entity;

import com.taskrail.dto.CommentRequestDto;
import com.taskrail.dto.CommentResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "comment")
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name ="card_id")
    private Card card;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(CommentRequestDto requestDto, Card card, User user) {
        this.content = requestDto.getContent();
        this.user = user;
        this.card = card;
    }

    public void update(CommentRequestDto requestDto){
        this.content = requestDto.getContent();
    }
}
