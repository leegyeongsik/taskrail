package com.taskrail.repository;


import com.taskrail.entity.Board;
import com.taskrail.entity.Card;
import com.taskrail.entity.Columns;
import com.taskrail.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface BoardRepositoryCustom {
    List<Board> getBoardchildList(Long userId);

    Long getBoardCount(Long boardId);

    List<User> getBoardUser(Long boardId);
    List<User> search(String name);
    List<Columns> getDetailBoard(Long boardId);
    List<Card> getColumnCard(Long columnId);

}
