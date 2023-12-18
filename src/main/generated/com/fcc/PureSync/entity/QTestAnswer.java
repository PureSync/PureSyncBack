package com.fcc.PureSync.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTestAnswer is a Querydsl query type for TestAnswer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTestAnswer extends EntityPathBase<TestAnswer> {

    private static final long serialVersionUID = 1612551253L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTestAnswer testAnswer = new QTestAnswer("testAnswer");

    public final NumberPath<Long> ansSeq = createNumber("ansSeq", Long.class);

    public final DateTimePath<java.time.LocalDateTime> ansWdate = createDateTime("ansWdate", java.time.LocalDateTime.class);

    public final QMember member;

    public final StringPath testAns = createString("testAns");

    public final NumberPath<Integer> testSeq = createNumber("testSeq", Integer.class);

    public final NumberPath<Integer> total = createNumber("total", Integer.class);

    public QTestAnswer(String variable) {
        this(TestAnswer.class, forVariable(variable), INITS);
    }

    public QTestAnswer(Path<? extends TestAnswer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTestAnswer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTestAnswer(PathMetadata metadata, PathInits inits) {
        this(TestAnswer.class, metadata, inits);
    }

    public QTestAnswer(Class<? extends TestAnswer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

