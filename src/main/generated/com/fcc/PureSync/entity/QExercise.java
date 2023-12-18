package com.fcc.PureSync.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QExercise is a Querydsl query type for Exercise
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExercise extends EntityPathBase<Exercise> {

    private static final long serialVersionUID = -593530755L;

    public static final QExercise exercise = new QExercise("exercise");

    public final NumberPath<Long> ecSeq = createNumber("ecSeq", Long.class);

    public final StringPath elDate = createString("elDate");

    public final NumberPath<Long> elSeq = createNumber("elSeq", Long.class);

    public final NumberPath<Integer> elTime = createNumber("elTime", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> elWdate = createDateTime("elWdate", java.time.LocalDateTime.class);

    public final NumberPath<Long> memSeq = createNumber("memSeq", Long.class);

    public QExercise(String variable) {
        super(Exercise.class, forVariable(variable));
    }

    public QExercise(Path<? extends Exercise> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExercise(PathMetadata metadata) {
        super(Exercise.class, metadata);
    }

}

