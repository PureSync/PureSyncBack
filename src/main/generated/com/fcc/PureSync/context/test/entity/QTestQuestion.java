package com.fcc.PureSync.context.test.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTestQuestion is a Querydsl query type for TestQuestion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTestQuestion extends EntityPathBase<TestQuestion> {

    private static final long serialVersionUID = -8447668L;

    public static final QTestQuestion testQuestion = new QTestQuestion("testQuestion");

    public final StringPath queContents = createString("queContents");

    public final NumberPath<Long> queNum = createNumber("queNum", Long.class);

    public final NumberPath<Long> queSeq = createNumber("queSeq", Long.class);

    public final NumberPath<Long> testSeq = createNumber("testSeq", Long.class);

    public QTestQuestion(String variable) {
        super(TestQuestion.class, forVariable(variable));
    }

    public QTestQuestion(Path<? extends TestQuestion> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTestQuestion(PathMetadata metadata) {
        super(TestQuestion.class, metadata);
    }

}

