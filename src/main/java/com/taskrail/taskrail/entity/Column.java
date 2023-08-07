package com.taskrail.taskrail.entity;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Column {

    /**
     * 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @jakarta.persistence.Column(length = 255)
    private String title;
    /**
     * 생성자 - 약속된 형태로만 생성가능하도록 합니다.
     */

    public Column(String title) {
        this.title = title;
    }

    /**
     * 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
     */

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    @OneToMany(mappedBy = "column")
    private List<Card> cards = new ArrayList<>();




    /**
     * 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
     */


    /**
     * 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
     */

    public void setTitle(String title) {
        this.title = title;
    }
}
