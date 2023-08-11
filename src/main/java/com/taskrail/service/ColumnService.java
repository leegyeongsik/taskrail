package com.taskrail.service;

import com.taskrail.dto.ColumnRequestDto;
import com.taskrail.dto.ColumnResponseDto;
import com.taskrail.dto.UserResponseDto;
import com.taskrail.entity.Board;
import com.taskrail.entity.Columns;
import com.taskrail.entity.User;
import com.taskrail.repository.BoardRepository;
import com.taskrail.repository.ColumnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColumnService {

    private final ColumnRepository columnRepository;
    private final BoardRepository boardRepository;
    private final BoardService boardService;


    // 보드 내 모든 컬럼 조회
    public List<ColumnResponseDto> getBoardColumns(Board board) {
        List<ColumnResponseDto> columns = board.getColumns().stream()
                .map(ColumnResponseDto::new)
                .sorted(Comparator.comparing(ColumnResponseDto::getPos)) // 컬럼 위치 순 정렬
                .collect(Collectors.toList());
        return columns;
    }

    // 컬럼 생성
    public ColumnResponseDto createColumn(User user, ColumnRequestDto columnRequestDto) {

        Board board = findBoard(columnRequestDto.getBoardId());

        //  보드에 초대된 유저인지 확인
        if (!isUserInvited(board.getBoardId(), user.getId()) && !user.equals(board.getUser())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        Columns column = new Columns(columnRequestDto.getName());

        int pos = getEndPos(board);
        column.setPos(pos);  // 칼럼 위치 설정
        column.setBoard(board); // 외래키 설정

        Columns savedColumn = columnRepository.save(column);

        return new ColumnResponseDto(savedColumn);
    }


    // 컬럼 이름 변경
    @Transactional
    public ColumnResponseDto updateColumnTitle(User user, Long id, ColumnRequestDto columnRequestDto) {
        Board board = findBoard(columnRequestDto.getBoardId());

        if (!isUserInvited(board.getBoardId(), user.getId()) && !user.equals(board.getUser())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        Columns column = findColumn(id);
        column.setName(columnRequestDto.getName());

        return new ColumnResponseDto(column);
    }

    // 컬럼 삭제
    public void deleteColumn(User user, Long id) {
        Columns column = findColumn(id);
        Long boardId = column.getBoard().getBoardId();
        Board board = findBoard(boardId);

        // 보드에 초대된 사람이나 호스트인지 확인
        if (!isUserInvited(boardId, user.getId()) && !user.equals(board.getUser())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }


        int deletedPos = column.getPos();

        columnRepository.delete(column);

        // 삭제한 컬럼의 위치(pos)를 기준으로 다른 컬럼들의 위치 조정
        List<Columns> otherColumns = column.getBoard().getColumns();
        for (Columns otherColumn : otherColumns) {
            if (otherColumn != column && otherColumn.getPos() > deletedPos) {
                otherColumn.setPos(otherColumn.getPos() - 1);
                columnRepository.save(otherColumn);
            }
        }

    }

    // 컬럼 왼쪽 or 오른쪽으로 옮기는 메서드
    public void moveColumn(User user, Long id, int offset) {
        Columns column = findColumn(id);
        Long boardId = column.getBoard().getBoardId();
        Board board = findBoard(boardId);

        if (!isUserInvited(boardId, user.getId()) && !user.equals(board.getUser())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
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

    public Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("보드가 존재하지 않습니다." + id)
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

    // 보드에 초대된 유저인지 확인
    public boolean isUserInvited(Long boardId, Long userId) {
        List<UserResponseDto> invitedUsers = boardService.getBoardUser(boardId);
        for (UserResponseDto user : invitedUsers) {
            if (user.getUserId().equals(userId)) {
                return true; // 초대된 유저 목록에 포함되어 있으면 true
            }
        }
        return false; // 초대된 유저 목록에 포함되어 있지 않으면 false
    }


   // @Transactional
    public void dragColumn(Long columnId, int newPosition) {
        Columns column = findColumn(columnId);
        column.setPos(newPosition);
        columnRepository.save(column);
    }
}
