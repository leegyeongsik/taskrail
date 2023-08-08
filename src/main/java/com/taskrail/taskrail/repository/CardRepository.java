package com.taskrail.taskrail.repository;

import com.taskrail.taskrail.dto.CardResponseDto;
import com.taskrail.taskrail.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findAllByColumn_IdOrderByOrdersDesc(Long columnId);
}
