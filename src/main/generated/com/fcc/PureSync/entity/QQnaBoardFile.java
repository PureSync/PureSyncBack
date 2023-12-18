package com.fcc.PureSync.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQnaBoardFile is a Querydsl query type for QnaBoardFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQnaBoardFile extends EntityPathBase<QnaBoardFile> {

    private static final long serialVersionUID = -915549149L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQnaBoardFile qnaBoardFile = new QQnaBoardFile("qnaBoardFile");

    public final StringPath fileUrl = createString("fileUrl");

    public final QQnaBoard qnaBoard;

    public final StringPath qnaBoardFileName = createString("qnaBoardFileName");

    public final NumberPath<Long> qnaBoardFileSeq = createNumber("qnaBoardFileSeq", Long.class);

    public final NumberPath<Long> qnaBoardFileSize = createNumber("qnaBoardFileSize", Long.class);

    public QQnaBoardFile(String variable) {
        this(QnaBoardFile.class, forVariable(variable), INITS);
    }

    public QQnaBoardFile(Path<? extends QnaBoardFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQnaBoardFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQnaBoardFile(PathMetadata metadata, PathInits inits) {
        this(QnaBoardFile.class, metadata, inits);
    }

    public QQnaBoardFile(Class<? extends QnaBoardFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.qnaBoard = inits.isInitialized("qnaBoard") ? new QQnaBoard(forProperty("qnaBoard"), inits.get("qnaBoard")) : null;
    }

}

