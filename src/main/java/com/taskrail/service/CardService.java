package com.taskrail.service;

import com.taskrail.dto.CardAssignUserRequestDto;
import com.taskrail.dto.CardRequestDto;
import com.taskrail.dto.CardResponseDto;
import com.taskrail.dto.CommentResponseDto;
import com.taskrail.entity.*;
import com.taskrail.repository.CardRepository;
import com.taskrail.repository.CardRoleRepository;
import com.taskrail.repository.ColumnRepository;
import com.taskrail.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardService {
    private final CardRepository cardRepository;
    private final ColumnRepository columnRepository;
    private final UserRepository userRepository;
    private final CardRoleRepository cardRoleRepository;
    public List<CardResponseDto> getCards(Long columnId) {
        return cardRepository.findAllByColumn_IdOrderByOrdersDesc(columnId).stream().map(CardResponseDto::new).toList();

    }

    public void createCard(Long columnId, CardRequestDto requestDto, User user) {
        userRepository.findById(user.getId()).orElseThrow(
                ()->new NullPointerException("유저가 없습니다.")
        );
        Columns column = columnRepository.findById(columnId).orElseThrow(
                ()->new NullPointerException("컬럼이 없습니다.")
        );

        List<Card> cards = cardRepository.findAll();

        try{
            Long orders = cards.get(cards.size() - 1).getOrders();
            Card card = new Card(requestDto, column, orders+1, user);
            cardRepository.save(card);
        }catch (IndexOutOfBoundsException e){
            Long orders = 1L;
            Card card = new Card(requestDto, column, orders, user);
            cardRepository.save(card);
        }


    }

    public void deleteCard(Long cardId, User user) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new NullPointerException("카드가 없습니다.")
        );

        if(!card.getUser().getId().equals(user.getId())){
            throw new IllegalArgumentException("작성한 유저가 아닙니다.");
        }
        cardRepository.delete(card);
    }


    @Transactional
    public void updateCard(Long cardId, CardRequestDto requestDto, User user) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new IllegalArgumentException("카드가 없습니다.")
        );

        if(!card.getUser().getId().equals(user.getId())){
            throw new IllegalArgumentException("작성한 유저가 아닙니다.");
        }
        card.update(requestDto);
    }

    public CardResponseDto getCardOne(Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new IllegalArgumentException("카드가 없습니다.")
        );

        return new CardResponseDto(card);
    }

    @Transactional
    public void updatePrevCard(Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new IllegalArgumentException("카드가 없습니다.")
        );

        if(card.getColumn().getId() == 1){
            throw new IllegalArgumentException("앞으로 이동할 수 없습니다.");
        }
        Columns column = columnRepository.findById(card.getColumn().getId()-1).orElseThrow(
                ()-> new IllegalArgumentException("앞으로 이동할 수 없습니다.")
        );
        //jpa sql 직접 사용해보기, 어노테이션 query
        // update
        // set columnss = columnss -1
        // where cardId = cardId


        card.updatePrev(column);
    }

    @Transactional
    public void updateNextCard(Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new IllegalArgumentException("카드가 없습니다.")
        );
        List<Card> cards = cardRepository.findAll();

        Long lastIndex = cards.get(cards.size() - 1).getColumn().getId();

        if(card.getColumn().getId().equals(lastIndex)){
            throw new IllegalArgumentException("뒤로 이동할 수 없습니다.");
        }
        Columns column = columnRepository.findById(card.getColumn().getId()+1).orElseThrow(
                ()-> new IllegalArgumentException("뒤로 이동할 수 없습니다.")
        );
        card.updateNext(column);
    }

    @Transactional
    public void updateUpCard(Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new NullPointerException("카드가 없습니다.")
        );

        if(card.getOrders().equals(1L)){
            throw new IllegalArgumentException("위로 이동할 수 없습니다.");
        }else{
            Card card2 = cardRepository.findByOrders(card.getOrders() - 1).orElseThrow(
                    () -> new NullPointerException("카드가 없습니다.")
            );

            card.updateUp(card.getOrders());
            card2.updateDown(card2.getOrders());
        }
    }

    @Transactional
    public void updateDownCard(Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new IllegalArgumentException("카드가 없습니다.")
        );

        List<Card> cards = cardRepository.findAll();

        Long max = cards.get(0).getOrders();
        for (Card value : cards) {
            if (max < value.getOrders()) {
                max = value.getOrders();
            }
        }

        if(card.getOrders().equals(max)){
            throw new IllegalArgumentException("아래로 이동할 수 없습니다.");
        }else{
            Card card2 = cardRepository.findByOrders(card.getOrders() + 1).orElseThrow(
                    () -> new NullPointerException("카드가 없습니다.")
            );
            card.updateDown(card.getOrders());
            card2.updateUp(card2.getOrders());

        }
    }

    public void cardAssignUser(Long cardId, CardAssignUserRequestDto requestDto, User user) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new IllegalArgumentException("카드가 없습니다.")
        );

        if(!card.getUser().getId().equals(user.getId())){
            throw new IllegalArgumentException("작성한 유저가 아닙니다.");
        }
        //카드 작성자 추가
        requestDto.add(user.getId());

        //할당 인원 추가
        List<Long>userIdList=requestDto.getUser_id();

        for (Long userId : userIdList) {
            User addUser = userRepository.findById(userId).orElseThrow(
                    () -> new NullPointerException("유저가 없습니다.")
            );
            CardRole cardRole = new CardRole(card,addUser);
            cardRoleRepository.save(cardRole);
        }





    }
}
