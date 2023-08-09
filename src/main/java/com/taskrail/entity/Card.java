package com.taskrail.entity;

import com.taskrail.dto.CardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "card")
@Getter
@NoArgsConstructor
public class Card extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String color;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orders;

    private LocalDateTime due_date;

    @ManyToOne
    @JoinColumn(name ="column_id")
    private Columns column;

    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;

//    //댓글 리스트
//    @OneToMany(mappedBy = "card")
//    Set<CommentResponseDto> commentList = new LinkedHashSet<>();

    public Card(CardRequestDto requestDto, Columns column, Long orders, User user){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.color = requestDto.getColor();
        this.due_date = requestDto.getDue_date();
        this.column = column;
        this.orders = orders;
        this.user = user;

    }

    public void update(CardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.color = requestDto.getColor();
        this.due_date = requestDto.getDue_date();
    }


    public void updateNext(Columns column) {
        this.column = column;
    }
    public void updatePrev(Columns column) {
        this.column = column;
    }
    public void updateUp(Long orders) {
        this.orders = orders-1;
    }
    public void updateDown(Long orders) {
        this.orders = orders+1;
    }
}
