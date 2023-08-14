package com.taskrail.service;

import com.taskrail.dto.*;
import com.taskrail.entity.*;
import com.taskrail.repository.*;
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
    private final BoardRepository boardRepository;
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
            cardRoleRepository.save(new CardRole(card,user));
        }catch (IndexOutOfBoundsException e){
            Long orders = 1L;
            Card card = new Card(requestDto, column, orders, user);


            cardRepository.save(card);
            cardRoleRepository.save(new CardRole(card,user));
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

        int columnPos = card.getColumn().getPos();
        Long boardId = card.getColumn().getBoard().getBoardId();

        // 해당 카드가 속한 칼럼의 pos 가 2이상 이여야 함.
        int prevColumnPos = (columnPos>1) ? columnPos-1 : 0;
        if(prevColumnPos == 0){
            throw new IllegalArgumentException("앞에 위치한 칼럼이 없습니다.");
        }

        Columns prevColumn = columnRepository.findByPosAndBoard_BoardId(prevColumnPos,boardId);
        log.info("prevColumn: " + prevColumn.getName());

        card.updatePrev(prevColumn);
        log.info("update card Column: " + card.getColumn().getName());


    }

    @Transactional
    public void updateNextCard(Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(
            () -> new IllegalArgumentException("카드가 없습니다.")
        );

        int columnPos = card.getColumn().getPos();
        Long boardId = card.getColumn().getBoard().getBoardId();

        // 해당 카드가 속한 보드의 칼럼 중 Pos 가 가장 높은 값 가져오기
        int largestPos = columnRepository.findItemWithLargestPos(boardId);

        // 가장 큰 pos 칼럼에 속한 카드이거나, 그것보다 더높은 pos 의 칼럼에 속할 경우(이경우는 데이터가 잘못된 경우임)
        int nextColumnPos = (largestPos>=columnPos) ? columnPos+1 : -1;

        if(nextColumnPos == -1){
            throw new IllegalArgumentException("뒤에 위치한 칼럼이 없습니다.");
        }

        Columns nextColumn = columnRepository.findByPosAndBoard_BoardId(nextColumnPos,boardId);
        log.info("nextColumn: " + nextColumn.getName());

        card.updateNext(nextColumn);
        log.info("update card Column: " + card.getColumn().getName());
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

        List<Long> userIdList = requestDto.getUser_id();
        List<CardRole> cardRoleList = cardRoleRepository.findAllByCard_Id(cardId);


        for (Long userId : userIdList) {
            User addUser = userRepository.findById(userId).orElseThrow(
                    () -> new NullPointerException("유저가 없습니다.")
            );

            for (CardRole cardRole : cardRoleList) {

                if(cardRole.getUser().getId().equals(userId)){
                    if(!cardRole.getUser().getId().equals(user.getId())){
                        throw new IllegalArgumentException("이미 할당된 유저가 있습니다.");
                    }
                }
            }


            CardRole cardRole = new CardRole(card,addUser);
            cardRoleRepository.save(cardRole);
        }
    }
    public List<UserResponseDto> getAddRoleUser(Long boardId, Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                ()->new NullPointerException("카드가 없습니다.")
        );
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        List<User> userList = boardRepository.getBoardUser(boardId);
        for (User user : userList) {
            if(card.getUser().getId().equals(user.getId())){
                continue;
            }
            userResponseDtoList.add(new UserResponseDto(user));
        }

        return userResponseDtoList;
    }

    public void cardAssignUpdateUser(Long cardId, CardAssignUserRequestDto requestDto, User user) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new IllegalArgumentException("카드가 없습니다.")
        );

        if(!card.getUser().getId().equals(user.getId())){
            throw new IllegalArgumentException("작성한 유저가 아닙니다.");
        }

        //추가할 작업자 명단
        List<Long> userIdList = requestDto.getUser_id();

        //카드 작성자 찾기
        CardRole hostCardRole = cardRoleRepository.findByUser_Id(card.getUser().getId()).orElseThrow(
                () -> new IllegalArgumentException("카드 작성자가 없습니다.")
        );

        //작업명단 초기화
        cardRoleRepository.deleteAll();

        //카드 작성자 작업명단 추가
        cardRoleRepository.save(hostCardRole);


        //추가할 유저 리스트
        for (Long userId : userIdList) {

            //추가할 유저 정보
            User addUser = userRepository.findById(userId).orElseThrow(
                    () -> new NullPointerException("유저가 없습니다.")
            );

            CardRole cardRole = new CardRole(card,addUser);
            cardRoleRepository.save(cardRole);
        }
    }
}
