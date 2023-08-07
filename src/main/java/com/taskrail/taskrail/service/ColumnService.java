package com.taskrail.taskrail.service;

import com.taskrail.taskrail.dto.ColumnRequestDto;
import com.taskrail.taskrail.dto.ColumnResponseDto;
import com.taskrail.taskrail.entity.Column;
import com.taskrail.taskrail.entity.User;
import com.taskrail.taskrail.repository.ColumnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ColumnService {
    private final ColumnRepository columnRepository;

    public ColumnResponseDto createColumn(User user, ColumnRequestDto columnRequestDto) {
        Column column = new Column(columnRequestDto.getTitle());
        columnRepository.save(column);
        return new ColumnResponseDto(column);
    }


    @Transactional
    public ColumnResponseDto updateColumnTitle(User user, Long id, ColumnRequestDto columnRequestDto) {
        Column column = findColumn(id);
        column.setTitle(columnRequestDto.getTitle());
        return new ColumnResponseDto(column);
    }

    public void deleteColumn(User user, Long id) {
        Column column = findColumn(id);
        columnRepository.delete(column);
    }

    public Column findColumn(Long id) {
        return columnRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("컬럼이 존재하지 않습니다.")
        );
    }


}
