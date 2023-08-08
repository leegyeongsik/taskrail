package com.taskrail.taskrail.service;

import com.taskrail.taskrail.dto.CardRequestDto;
import com.taskrail.taskrail.dto.CardResponseDto;
import com.taskrail.taskrail.entity.Card;
import com.taskrail.taskrail.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardService {
    private final CardRepository cardRepository;
    private final ColumnRepository columnRepository;
    public List<CardResponseDto> getCards(Long columnId) {
        return cardRepository.findAllByColumn_IdOrderByOrdersDesc(columnId).stream().map(CardResponseDto::new).toList();
    }

    public void createCard(Long columnId, CardRequestDto requestDto) {
        Column column = columnRepository.findById(columnId).orElseThrow(
                ()->new IllegalArgumentException("컬럼이 없습니다.")
        );
        List<Card> cards = cardRepository.findAll();

        Long orders = cards.get(cards.size() - 1).getOrders();

        Card card = new Card(requestDto, column, orders+1);
        cardRepository.save(card);
    }

    public void deleteCard(Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new IllegalArgumentException("카드가 없습니다.")
        );
        cardRepository.delete(card);
    }


    @Transactional
    public void updateCard(Long cardId, CardRequestDto requestDto) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new IllegalArgumentException("카드가 없습니다.")
        );
        card.update(requestDto);
    }
}
