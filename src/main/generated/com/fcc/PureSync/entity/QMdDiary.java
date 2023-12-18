package com.fcc.PureSync.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMdDiary is a Querydsl query type for MdDiary
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMdDiary extends EntityPathBase<MdDiary> {

    private static final long serialVersionUID = -1419643609L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMdDiary mdDiary = new QMdDiary("mdDiary");

    public final StringPath dyContents = createString("dyContents");

    public final StringPath dyDate = createString("dyDate");

    public final NumberPath<Long> dySeq = createNumber("dySeq", Long.class);

    public final BooleanPath dyStatus = createBoolean("dyStatus");

    public final StringPath dyTitle = createString("dyTitle");

    public final DateTimePath<java.time.LocalDateTime> dyWdate = createDateTime("dyWdate", java.time.LocalDateTime.class);

    public final QEmotion emotion;

    public final QMember member;

    public QMdDiary(String variable) {
        this(MdDiary.class, forVariable(variable), INITS);
    }

    public QMdDiary(Path<? extends MdDiary> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMdDiary(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMdDiary(PathMetadata metadata, PathInits inits) {
        this(MdDiary.class, metadata, inits);
    }

    public QMdDiary(Class<? extends MdDiary> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.emotion = inits.isInitialized("emotion") ? new QEmotion(forProperty("emotion")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

