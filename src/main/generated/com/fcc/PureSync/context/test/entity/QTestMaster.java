package com.fcc.PureSync.context.test.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTestMaster is a Querydsl query type for TestMaster
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTestMaster extends EntityPathBase<TestMaster> {

    private static final long serialVersionUID = 1355688264L;

    public static final QTestMaster testMaster = new QTestMaster("testMaster");

    public final StringPath testName = createString("testName");

    public final NumberPath<Long> testSeq = createNumber("testSeq", Long.class);

    public QTestMaster(String variable) {
        super(TestMaster.class, forVariable(variable));
    }

    public QTestMaster(Path<? extends TestMaster> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTestMaster(PathMetadata metadata) {
        super(TestMaster.class, metadata);
    }

}

