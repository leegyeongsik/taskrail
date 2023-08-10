package com.taskrail.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardRole is a Querydsl query type for BoardRole
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardRole extends EntityPathBase<BoardRole> {

    private static final long serialVersionUID = -642676645L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardRole boardRole = new QBoardRole("boardRole");

    public final QBoard board;

    public final NumberPath<Long> boardRole_Id = createNumber("boardRole_Id", Long.class);

    public final QUser user;

    public QBoardRole(String variable) {
        this(BoardRole.class, forVariable(variable), INITS);
    }

    public QBoardRole(Path<? extends BoardRole> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardRole(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardRole(PathMetadata metadata, PathInits inits) {
        this(BoardRole.class, metadata, inits);
    }

    public QBoardRole(Class<? extends BoardRole> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board"), inits.get("board")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

