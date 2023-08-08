package com.taskrail.taskrail.entity;

import com.taskrail.taskrail.dto.CardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "card")
@Getter
@NoArgsConstructor
public class Card extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @jakarta.persistence.Column(nullable = false)
    private String title;

    @jakarta.persistence.Column(nullable = false)
    private String content;

    @jakarta.persistence.Column(nullable = false)
    private String color;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orders;

    private LocalDateTime due_date;

    @ManyToOne
    @JoinColumn(name ="column_id")
    private Column column;

    public Card(CardRequestDto requestDto, Column column, Long orders){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.color = requestDto.getColor();
        this.due_date = requestDto.getDue_date();
        this.column = column;
        this.orders = orders;

    }

    public void update(CardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.color = requestDto.getColor();
        this.due_date = requestDto.getDue_date();
    }

//    //댓글 리스트
//    @OneToMany(mappedBy = "card")
//    Set<Comment> commentList = new LinkedHashSet<>();
}
