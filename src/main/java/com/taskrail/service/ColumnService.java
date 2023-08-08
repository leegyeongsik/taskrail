package com.taskrail.service;

import com.taskrail.dto.ColumnRequestDto;
import com.taskrail.dto.ColumnResponseDto;
import com.taskrail.entity.Board;
import com.taskrail.entity.Columns;
import com.taskrail.entity.User;
import com.taskrail.repository.BoardRepository;
import com.taskrail.repository.ColumnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColumnService {

    private final ColumnRepository columnRepository;
    private final BoardRepository boardRepository;


    // 컬럼 생성
    @Transactional
    public Columns createColumn(User user, ColumnRequestDto columnRequestDto) {
        Columns column = new Columns(columnRequestDto.getName());
        Board board = boardRepository.findById(columnRequestDto.getBoardId()).orElseThrow(
                () -> new IllegalArgumentException("보드가 존재하지 않습니다.")
        );
        int pos = getEndPos(board);
        column.setPos(pos);  // 칼럼 위치 설정
        column.setBoard(board);

        return columnRepository.save(column);
    }


    // 컬럼 이름 변경
    @Transactional
    public void updateColumnTitle(User user, Long id, ColumnRequestDto columnRequestDto) {
        Columns column = findColumn(id);
        column.setName(columnRequestDto.getName());
    }

    // 컬럼 삭제
    public void deleteColumn(User user, Long id) {
        Columns column = findColumn(id);
        columnRepository.delete(column);
    }

    // 컬럼 왼쪽 or 오른쪽으로 옮기는 메서드
    public void moveColumn(Long id, int offset) {
        Columns column = findColumn(id);
        int currentPos = column.getPos();
        int newPos = currentPos + offset; // offset이 1이면 오른쪽으로, -1이면 왼쪽으로 이동

        if (newPos < 1) {
            return; // 가장 왼쪽에 있을 때 왼쪽으로 이동 안되게 처리
        }

        // 옮길 자리에 다른 컬럼이 있으면 위치를 바꿔준다.
        List<Columns> otherColumns = column.getBoard().getColumns();
        for (Columns otherColumn : otherColumns) {
            if (otherColumn != column && otherColumn.getPos() == newPos) {
                otherColumn.setPos(currentPos);
                columnRepository.save(otherColumn);
            }
        }
        column.setPos(newPos);
        columnRepository.save(column);
    }

    public Columns findColumn(Long id) {
        return columnRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("컬럼이 존재하지 않습니다." + id)
        );
    }

    // 컬럼 생성시 위치 설정
    private int getEndPos(Board board) {
        List<Columns> columns = board.getColumns();
        if (columns.isEmpty()) {
            return 1; // 컬럼이 하나도 없을 땐 1 반환
        } else {
            int endPos = columns.stream().mapToInt(Columns::getPos).max().orElse(0);
            return endPos + 1; // 가장 오른쪽에 있는 컬럼의 위치에서 + 1;
        }

    }


}
