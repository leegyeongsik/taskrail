package com.taskrail.repository;


import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.taskrail.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
    QBoard board = QBoard.board;
    QUser user = QUser.user;
    QBoardRole boardRole = QBoardRole.boardRole;
    QColumns columns = QColumns.columns;

    QCard card = QCard.card;


    @Override
    public List<Board> getBoardchildList(Long userId) { // 조인해서 같은거찾고 board_role에서 userid같은거가져와서 초대받은애들만 반환
        return jpaQueryFactory.select(board)
                .from(board)
                .leftJoin(board.boardAuthSet).fetchJoin()
                .where(
                        BoardUser(userId)
                )
                .fetch();
    }
    @Override
    public Long getBoardCount(Long boardId) { // 조인해서 총 카운트수 반환
        return jpaQueryFactory.select(board.count())
                .from(board)
                .leftJoin(board.boardAuthSet) // 페치조인쓰면 오류남
                .where(
                        BoardUserCnt(boardId)
                )
                .fetchOne();
    }

    @Override
    public List<User> getBoardUser(Long boardId) { // 해당보드에 초대되어있는 유저목록 반환
        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(Order.DESC, board.createdAt);

        return jpaQueryFactory.select(boardRole.user)
                .from(boardRole)
                .leftJoin(boardRole.board)
                .where(
                        whoBoardUser(boardId)
                )
                .orderBy(orderSpecifier)
                .fetch();
    }
    @Override
    public List<User> search(String name) {
        return jpaQueryFactory.selectFrom(user)
                .where(user.name.contains(name))
                .fetch();
    }
    @Override
    public List<Columns> getDetailBoard(Long boardId) {
        return jpaQueryFactory.selectFrom(columns)
                .leftJoin(columns.board).fetchJoin()
                .where(detailBoard(boardId))
                .fetch();
    }
    @Override
    public  List<Card> getColumnCard(Long columnId){
        return jpaQueryFactory.selectFrom(card)
                .leftJoin(card.column).fetchJoin()
                .where(ColumnCard(columnId))
                .fetch();
    }


    private BooleanExpression BoardUserCnt(Long boardId){
        return Objects.nonNull(boardId) ? board.boardAuthSet.any().board.boardId.eq(boardId) : null;
    }
    private BooleanExpression whoBoardUser(Long boardId){
        return Objects.nonNull(boardId) ? boardRole.board.boardId.eq(boardId) : null;
    }
    private BooleanExpression BoardUser(Long userId){
        return Objects.nonNull(userId) ? board.boardAuthSet.any().user.id.eq(userId) : null;
    }
    private BooleanExpression detailBoard(Long boardId){
        return Objects.nonNull(boardId) ? columns.board.boardId.eq(boardId) : null;
    }
    private BooleanExpression ColumnCard(Long columnId){
        return Objects.nonNull(columnId) ? card.column.id.eq(columnId) : null;
    }



}
