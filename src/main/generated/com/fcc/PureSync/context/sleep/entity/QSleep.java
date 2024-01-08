package com.fcc.PureSync.context.sleep.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSleep is a Querydsl query type for Sleep
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSleep extends EntityPathBase<Sleep> {

    private static final long serialVersionUID = -322553816L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSleep sleep = new QSleep("sleep");

    public final com.fcc.PureSync.entity.QMember member;

    public final NumberPath<Integer> sleepCategory = createNumber("sleepCategory", Integer.class);

    public final NumberPath<Integer> sleepColor = createNumber("sleepColor", Integer.class);

    public final StringPath sleepDate = createString("sleepDate");

    public final DateTimePath<java.time.LocalDateTime> sleepGodate = createDateTime("sleepGodate", java.time.LocalDateTime.class);

    public final NumberPath<Long> sleepSeq = createNumber("sleepSeq", Long.class);

    public final DateTimePath<java.time.LocalDateTime> sleepWdate = createDateTime("sleepWdate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> sleepWudate = createDateTime("sleepWudate", java.time.LocalDateTime.class);

    public QSleep(String variable) {
        this(Sleep.class, forVariable(variable), INITS);
    }

    public QSleep(Path<? extends Sleep> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSleep(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSleep(PathMetadata metadata, PathInits inits) {
        this(Sleep.class, metadata, inits);
    }

    public QSleep(Class<? extends Sleep> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.fcc.PureSync.entity.QMember(forProperty("member")) : null;
    }

}

