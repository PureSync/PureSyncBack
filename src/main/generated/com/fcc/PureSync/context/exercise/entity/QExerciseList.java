package com.fcc.PureSync.context.exercise.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QExerciseList is a Querydsl query type for ExerciseList
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExerciseList extends EntityPathBase<ExerciseList> {

    private static final long serialVersionUID = -1150528240L;

    public static final QExerciseList exerciseList = new QExerciseList("exerciseList");

    public final NumberPath<Double> ecCalc = createNumber("ecCalc", Double.class);

    public final StringPath ecName = createString("ecName");

    public final NumberPath<Long> ecSeq = createNumber("ecSeq", Long.class);

    public QExerciseList(String variable) {
        super(ExerciseList.class, forVariable(variable));
    }

    public QExerciseList(Path<? extends ExerciseList> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExerciseList(PathMetadata metadata) {
        super(ExerciseList.class, metadata);
    }

}

