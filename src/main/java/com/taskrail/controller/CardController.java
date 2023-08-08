package com.taskrail.controller;

import com.taskrail.dto.ApiResponseDto;
import com.taskrail.dto.CardRequestDto;
import com.taskrail.dto.CardResponseDto;
import com.taskrail.dto.RestApiResponseDto;
import com.taskrail.security.UserDetailsImpl;
import com.taskrail.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    //안쓰일것 같다.
    //1. 카드 컬럼 별 전체 조회
    @GetMapping("/columns/{columnId}/cards")
    public List<CardResponseDto> getCards(@PathVariable Long columnId){
        return cardService.getCards(columnId);
    }

    @GetMapping("/cards/{cardId}")
    public CardResponseDto getCardOne(@PathVariable Long cardId){
        return cardService.getCardOne(cardId);
    }

    //2. 카드 등록
    @PostMapping("/columns/{columnId}/cards")
    public ResponseEntity<ApiResponseDto> createCard(@PathVariable Long columnId, @RequestBody CardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails ){
        cardService.createCard(columnId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("카드 등록 완료", HttpStatus.CREATED.value()));
    }

    //3. 카드 삭제
    @DeleteMapping("/cards/{cardId}")
    public ResponseEntity<ApiResponseDto> deleteCard(@PathVariable Long cardId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        cardService.deleteCard(cardId, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("카드 삭제 완료", HttpStatus.OK.value()));
    }

    //4. 카드 수정
    @PutMapping("/cards/{cardId}")
    public ResponseEntity<ApiResponseDto> updateCard(@PathVariable Long cardId, @RequestBody CardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        cardService.updateCard(cardId, requestDto , userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("카드 수정 완료", HttpStatus.OK.value()));
    }

    //5. 카드 앞 컬럼으로 이동
    @PutMapping("/cards/{cardId}/prev")
    public ResponseEntity<ApiResponseDto> updatePrevCard(@PathVariable Long cardId){
        cardService.updatePrevCard(cardId);
        return ResponseEntity.ok().body(new ApiResponseDto("카드 이동 완료", HttpStatus.OK.value()));
    }
    //6. 카드 뒤 컬럼으로 이동
    @PutMapping("/cards/{cardId}/next")
    public ResponseEntity<ApiResponseDto> updateNextCard(@PathVariable Long cardId){
        cardService.updateNextCard(cardId);
        return ResponseEntity.ok().body(new ApiResponseDto("카드 이동 완료", HttpStatus.OK.value()));
    }

    //7. 사용자 할당인원 추가


    //8. 예외 처리
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<RestApiResponseDto> handleMethodArgumentNotValidException(IllegalArgumentException ex) {
        RestApiResponseDto restApiException = new RestApiResponseDto(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    //9. 예외 처리
    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<RestApiResponseDto> handleMethodArgumentNotValidException(NullPointerException ex) {
        RestApiResponseDto restApiException = new RestApiResponseDto(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }
}