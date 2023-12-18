package com.fcc.PureSync.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBody is a Querydsl query type for Body
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBody extends EntityPathBase<Body> {

    private static final long serialVersionUID = 908051143L;

    public static final QBody body = new QBody("body");

    public final NumberPath<Double> bodyHeight = createNumber("bodyHeight", Double.class);

    public final NumberPath<Long> bodySeq = createNumber("bodySeq", Long.class);

    public final DateTimePath<java.time.LocalDateTime> bodyWdate = createDateTime("bodyWdate", java.time.LocalDateTime.class);

    public final NumberPath<Double> bodyWeight = createNumber("bodyWeight", Double.class);

    public final NumberPath<Double> bodyWishBurncal = createNumber("bodyWishBurncal", Double.class);

    public final NumberPath<Double> bodyWishConscal = createNumber("bodyWishConscal", Double.class);

    public final NumberPath<Double> bodyWishWeight = createNumber("bodyWishWeight", Double.class);

    public final NumberPath<Long> memSeq = createNumber("memSeq", Long.class);

    public QBody(String variable) {
        super(Body.class, forVariable(variable));
    }

    public QBody(Path<? extends Body> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBody(PathMetadata metadata) {
        super(Body.class, metadata);
    }

}

