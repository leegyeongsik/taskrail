package com.taskrail.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// lombok
@Entity
@Getter

// jpa
// @Setter -> Entity 클래스에는 DB 정보 보호를 위해 미기재
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void update(String email, String password) {
        this.email = email;
        this.password = password;
    }

}