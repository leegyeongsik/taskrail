package com.taskrail.repository;

import com.taskrail.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findAllByColumn_IdOrderByOrdersDesc(Long columnId);

    Optional<Card> findByOrders(long ld);
}
