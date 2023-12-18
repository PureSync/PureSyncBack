package com.fcc.PureSync.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEmotion is a Querydsl query type for Emotion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmotion extends EntityPathBase<Emotion> {

    private static final long serialVersionUID = 367970582L;

    public static final QEmotion emotion = new QEmotion("emotion");

    public final NumberPath<Integer> emoField = createNumber("emoField", Integer.class);

    public final NumberPath<Long> emoSeq = createNumber("emoSeq", Long.class);

    public final StringPath emoState = createString("emoState");

    public QEmotion(String variable) {
        super(Emotion.class, forVariable(variable));
    }

    public QEmotion(Path<? extends Emotion> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmotion(PathMetadata metadata) {
        super(Emotion.class, metadata);
    }

}

