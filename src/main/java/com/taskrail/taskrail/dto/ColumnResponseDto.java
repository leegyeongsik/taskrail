package com.taskrail.taskrail.dto;

import com.taskrail.taskrail.entity.Card;
import com.taskrail.taskrail.entity.Column;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ColumnResponseDto {
    private String title;

    @OneToMany(mappedBy = "column")
    private List<Card> cards = new ArrayList<>();


    public ColumnResponseDto(Column column) {
        this.title = column.getTitle();
    }
}
