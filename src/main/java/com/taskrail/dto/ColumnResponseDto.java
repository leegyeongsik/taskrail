package com.taskrail.dto;

import com.taskrail.entity.Columns;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ColumnResponseDto {
    private Long id;
    private String name;

    @OneToMany(mappedBy = "column")
    private List<CardResponseDto> cards = new ArrayList<>();


    public ColumnResponseDto(Columns column) {
        this.id = column.getId();
        this.name = column.getName();
        this.cards = column.getCards().stream().map(CardResponseDto::new).collect(Collectors.toList());
    }
}
