package com.fcc.PureSync.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPositive is a Querydsl query type for Positive
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPositive extends EntityPathBase<Positive> {

    private static final long serialVersionUID = -1902049122L;

    public static final QPositive positive = new QPositive("positive");

    public final StringPath pvContents = createString("pvContents");

    public final NumberPath<Long> pvSeq = createNumber("pvSeq", Long.class);

    public final StringPath pvTalker = createString("pvTalker");

    public QPositive(String variable) {
        super(Positive.class, forVariable(variable));
    }

    public QPositive(Path<? extends Positive> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPositive(PathMetadata metadata) {
        super(Positive.class, metadata);
    }

}

