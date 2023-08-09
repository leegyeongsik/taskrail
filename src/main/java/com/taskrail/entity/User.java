package com.taskrail.entity;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

// lombok
@Entity
@Getter

// jpa
// @Setter -> Entity 클래스에는 DB 정보 보호를 위해 미기재
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // 사용자 아이디

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password; // 비밀번호

    private Long kakaoId;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String password, Long kakaoId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.kakaoId = kakaoId;
    }

    public void update(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User kakaoIdUpdate(long kakaoId){
        this.kakaoId = kakaoId;
        return this;
    }

}
