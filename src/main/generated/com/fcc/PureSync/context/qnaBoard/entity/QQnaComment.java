package com.fcc.PureSync.context.qnaBoard.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQnaComment is a Querydsl query type for QnaComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQnaComment extends EntityPathBase<QnaComment> {

    private static final long serialVersionUID = 1890721759L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQnaComment qnaComment = new QQnaComment("qnaComment");

    public final QQnaBoard qnaBoard;

    public final StringPath qnaCmtContents = createString("qnaCmtContents");

    public final NumberPath<Long> qnaCmtSeq = createNumber("qnaCmtSeq", Long.class);

    public final DateTimePath<java.time.LocalDateTime> qnaCmtWdate = createDateTime("qnaCmtWdate", java.time.LocalDateTime.class);

    public final StringPath qnaCmtWriter = createString("qnaCmtWriter");

    public QQnaComment(String variable) {
        this(QnaComment.class, forVariable(variable), INITS);
    }

    public QQnaComment(Path<? extends QnaComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQnaComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQnaComment(PathMetadata metadata, PathInits inits) {
        this(QnaComment.class, metadata, inits);
    }

    public QQnaComment(Class<? extends QnaComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.qnaBoard = inits.isInitialized("qnaBoard") ? new QQnaBoard(forProperty("qnaBoard"), inits.get("qnaBoard")) : null;
    }

}

