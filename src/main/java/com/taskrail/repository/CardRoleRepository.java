package com.taskrail.repository;

import com.taskrail.entity.Card;
import com.taskrail.entity.CardRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRoleRepository extends JpaRepository<CardRole, Long> {
    List<CardRole> findAllByCard_Id(Long card_id);

    Optional<CardRole> findByUser_Id(Long user_id);
}
