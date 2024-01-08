package com.fcc.PureSync.context.qnaBoard.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQnaBoard is a Querydsl query type for QnaBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQnaBoard extends EntityPathBase<QnaBoard> {

    private static final long serialVersionUID = -1375502234L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQnaBoard qnaBoard = new QQnaBoard("qnaBoard");

    public final com.fcc.PureSync.entity.QMember member;

    public final StringPath qnaBoardContents = createString("qnaBoardContents");

    public final ListPath<QnaBoardFile, QQnaBoardFile> qnaBoardFile = this.<QnaBoardFile, QQnaBoardFile>createList("qnaBoardFile", QnaBoardFile.class, QQnaBoardFile.class, PathInits.DIRECT2);

    public final StringPath qnaBoardName = createString("qnaBoardName");

    public final NumberPath<Long> qnaBoardSeq = createNumber("qnaBoardSeq", Long.class);

    public final NumberPath<Integer> qnaBoardStatus = createNumber("qnaBoardStatus", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> qnaBoardWdate = createDateTime("qnaBoardWdate", java.time.LocalDateTime.class);

    public final ListPath<QnaComment, QQnaComment> qnaComments = this.<QnaComment, QQnaComment>createList("qnaComments", QnaComment.class, QQnaComment.class, PathInits.DIRECT2);

    public QQnaBoard(String variable) {
        this(QnaBoard.class, forVariable(variable), INITS);
    }

    public QQnaBoard(Path<? extends QnaBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQnaBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQnaBoard(PathMetadata metadata, PathInits inits) {
        this(QnaBoard.class, metadata, inits);
    }

    public QQnaBoard(Class<? extends QnaBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.fcc.PureSync.entity.QMember(forProperty("member")) : null;
    }

}

