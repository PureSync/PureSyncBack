package com.fcc.PureSync.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMpMemRole is a Querydsl query type for MpMemRole
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMpMemRole extends EntityPathBase<MpMemRole> {

    private static final long serialVersionUID = 254184131L;

    public static final QMpMemRole mpMemRole = new QMpMemRole("mpMemRole");

    public final NumberPath<Long> memRoleSeq = createNumber("memRoleSeq", Long.class);

    public final NumberPath<Long> memSeq = createNumber("memSeq", Long.class);

    public final NumberPath<Integer> roleSeq = createNumber("roleSeq", Integer.class);

    public QMpMemRole(String variable) {
        super(MpMemRole.class, forVariable(variable));
    }

    public QMpMemRole(Path<? extends MpMemRole> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMpMemRole(PathMetadata metadata) {
        super(MpMemRole.class, metadata);
    }

}

