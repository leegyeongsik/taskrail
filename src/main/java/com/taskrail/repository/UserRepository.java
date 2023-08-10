package com.taskrail.repository;


import com.taskrail.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByName(String inputName);

    User findUserById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByKakaoId(Long kakaoId);

    boolean existsByName(String inputName);

    Optional<User> findByGoogleId(String googleId);
}
