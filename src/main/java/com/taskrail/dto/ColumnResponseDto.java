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
    private Long id;
    private String name;

//    @OneToMany(mappedBy = "column")
//    private List<Card> cards = new ArrayList<>();


    public ColumnResponseDto(Columns column) {
        this.id = column.getId();
        this.name = column.getName();
    }
}
