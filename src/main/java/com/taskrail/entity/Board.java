package com.taskrail.entity;

import com.taskrail.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "board")

public class Board extends Timestamped{

    /**
     * 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(name ="title",nullable = false, length = 500)
    private String title;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "description" , nullable = false)
    private String description;

    /**
     * 생성자 - 약속된 형태로만 생성가능하도록 합니다.
     */
    @Builder
    public Board(String title , String color , String description , User user){
        this.title=title;
        this.color = color;
        this.description = description;
        this.user = user;
    }

    /**
     * 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
     */
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private Set<BoardRole> boardAuthSet  = new LinkedHashSet<>();

//    @OneToMany(mappedBy = "board",cascade = CascadeType.REMOVE)
//    private Set<Column> Columns = new LinkedHashSet<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;


    /**
     * 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
     */


    /**
     * 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
     * ex (update 메소드)
     */
    public void updateBoard(BoardRequestDto boardRequestDto){
        this.description = boardRequestDto.getDescription();
        this.title = boardRequestDto.getTitle();
        this.color = boardRequestDto.getColor();


    }

}
