package com.taskrail.repository;

import com.taskrail.entity.Card;
import com.taskrail.entity.CardRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRoleRepository extends JpaRepository<CardRole, Long> {
    List<CardRole> findAllByCard_Id(Long card_id);
}
