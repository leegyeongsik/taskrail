package com.taskrail.taskrail.controller;

import com.taskrail.taskrail.dto.CardResponseDto;
import com.taskrail.taskrail.security.UserDetailsImpl;
import com.taskrail.taskrail.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    //1. 카드 컬럼 별 전체 조회
    @GetMapping("/columns/{columnId}/cards")
    public List<CardResponseDto> getCards(@PathVariable Long columnId){
        return cardService.getCards(columnId);
    }
}
