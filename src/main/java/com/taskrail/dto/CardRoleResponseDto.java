package com.taskrail.dto;

import com.taskrail.entity.CardRole;
import lombok.Getter;

@Getter
public class CardRoleResponseDto {
    private Long id;
    private Long cardId;
    private Long userId;
    private String userName;

    public CardRoleResponseDto(CardRole cardRole) {
        this.id = cardRole.getId();
        this.cardId = cardRole.getCard().getId();
        this.userId = cardRole.getUser().getId();
        this.userName = cardRole.getUser().getName();
    }
}
