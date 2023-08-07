package com.taskrail.taskrail.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "card")
@Getter
@NoArgsConstructor
public class Card extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private Long orders;

    private LocalDateTime due_date;

//    //댓글 리스트
//    @OneToMany(mappedBy = "card")
//    Set<Comment> commentList = new LinkedHashSet<>();
}
