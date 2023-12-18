package com.fcc.PureSync.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFood is a Querydsl query type for Food
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFood extends EntityPathBase<Food> {

    private static final long serialVersionUID = 908170627L;

    public static final QFood food = new QFood("food");

    public final NumberPath<Double> foodCar = createNumber("foodCar", Double.class);

    public final StringPath foodCheckbox = createString("foodCheckbox");

    public final NumberPath<Double> foodCol = createNumber("foodCol", Double.class);

    public final NumberPath<Double> foodFat = createNumber("foodFat", Double.class);

    public final NumberPath<Integer> foodKcal = createNumber("foodKcal", Integer.class);

    public final NumberPath<Integer> foodNa = createNumber("foodNa", Integer.class);

    public final StringPath foodName = createString("foodName");

    public final NumberPath<Double> foodPro = createNumber("foodPro", Double.class);

    public final NumberPath<Long> foodSeq = createNumber("foodSeq", Long.class);

    public final NumberPath<Double> foodSugar = createNumber("foodSugar", Double.class);

    public QFood(String variable) {
        super(Food.class, forVariable(variable));
    }

    public QFood(Path<? extends Food> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFood(PathMetadata metadata) {
        super(Food.class, metadata);
    }

}

