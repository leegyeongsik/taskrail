package com.taskrail.taskrail.controller;

import com.taskrail.taskrail.dto.ApiResponseDto;
import com.taskrail.taskrail.dto.CardRequestDto;
import com.taskrail.taskrail.dto.CardResponseDto;
import com.taskrail.taskrail.security.UserDetailsImpl;
import com.taskrail.taskrail.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    //2. 카드 등록
    @PostMapping("/columns/{columnId}/cards")
    public ResponseEntity<ApiResponseDto> createCard(@PathVariable Long columnId, @RequestBody CardRequestDto requestDto/*@AuthenticationPrincipal UserDetailsImpl userDetails */){
        cardService.createCard(columnId, requestDto/*, userDetails.getUser()*/);
        return ResponseEntity.ok().body(new ApiResponseDto("카드 등록 완료", HttpStatus.CREATED.value()));
    }

    //3. 카드 삭제
    @DeleteMapping("/cards/{cardId}")
    public ResponseEntity<ApiResponseDto> deleteCard(@PathVariable Long cardId/*, @AuthenticationPrincipal UserDetailsImpl userDetails*/){
        cardService.deleteCard(cardId/*, userDetails.getUser()*/);
        return ResponseEntity.ok().body(new ApiResponseDto("카드 삭제 완료", HttpStatus.OK.value()));
    }

    //4. 카드 수정
    @PutMapping("/cards/{cardId}")
    public ResponseEntity<ApiResponseDto> updateCard(@PathVariable Long cardId, @RequestBody CardRequestDto requestDto/*, @AuthenticationPrincipal UserDetailsImpl userDetails*/){
        cardService.updateCard(cardId, requestDto /*, userDetails.getUser()*/);
        return ResponseEntity.ok().body(new ApiResponseDto("카드 수정 완료", HttpStatus.OK.value()));
    }


}
