package com.taskrail.dto;

import com.taskrail.entity.Card;
import com.taskrail.entity.Columns;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ColumnResponseDto {
    private String name;

    @OneToMany(mappedBy = "column")
    private List<Card> cards = new ArrayList<>();
    private List<CardResponseDto> cardResponseDtos = new ArrayList<>();


    public ColumnResponseDto(Columns column) {
        this.name = column.getName();
    }

    public ColumnResponseDto(Columns column , List<CardResponseDto> cardResponseDtoList) {
        this.name = column.getName();
        this.cardResponseDtos = cardResponseDtoList;
    }
}
