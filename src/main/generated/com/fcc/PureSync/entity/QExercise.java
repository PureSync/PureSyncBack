package com.fcc.PureSync.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExercise is a Querydsl query type for Exercise
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExercise extends EntityPathBase<Exercise> {

    private static final long serialVersionUID = -593530755L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExercise exercise = new QExercise("exercise");

    public final StringPath elDate = createString("elDate");

    public final NumberPath<Long> elSeq = createNumber("elSeq", Long.class);

    public final NumberPath<Integer> elTime = createNumber("elTime", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> elWdate = createDateTime("elWdate", java.time.LocalDateTime.class);

    public final QExerciseList exerciseList;

    public final QMember member;

    public QExercise(String variable) {
        this(Exercise.class, forVariable(variable), INITS);
    }

    public QExercise(Path<? extends Exercise> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExercise(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExercise(PathMetadata metadata, PathInits inits) {
        this(Exercise.class, metadata, inits);
    }

    public QExercise(Class<? extends Exercise> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.exerciseList = inits.isInitialized("exerciseList") ? new QExerciseList(forProperty("exerciseList")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

