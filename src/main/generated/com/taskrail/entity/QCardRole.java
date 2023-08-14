package com.taskrail.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCardRole is a Querydsl query type for CardRole
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCardRole extends EntityPathBase<CardRole> {

    private static final long serialVersionUID = 438429671L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCardRole cardRole = new QCardRole("cardRole");

    public final QCard card;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUser user;

    public QCardRole(String variable) {
        this(CardRole.class, forVariable(variable), INITS);
    }

    public QCardRole(Path<? extends CardRole> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCardRole(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCardRole(PathMetadata metadata, PathInits inits) {
        this(CardRole.class, metadata, inits);
    }

    public QCardRole(Class<? extends CardRole> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.card = inits.isInitialized("card") ? new QCard(forProperty("card"), inits.get("card")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

