package com.fcc.PureSync.context.trash.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMdTrash is a Querydsl query type for MdTrash
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMdTrash extends EntityPathBase<MdTrash> {

    private static final long serialVersionUID = -1942576527L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMdTrash mdTrash = new QMdTrash("mdTrash");

    public final com.fcc.PureSync.context.member.entity.QMember member;

    public final StringPath tsContents = createString("tsContents");

    public final NumberPath<Long> tsSeq = createNumber("tsSeq", Long.class);

    public final BooleanPath tsStatus = createBoolean("tsStatus");

    public final DateTimePath<java.time.LocalDateTime> tsWdate = createDateTime("tsWdate", java.time.LocalDateTime.class);

    public QMdTrash(String variable) {
        this(MdTrash.class, forVariable(variable), INITS);
    }

    public QMdTrash(Path<? extends MdTrash> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMdTrash(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMdTrash(PathMetadata metadata, PathInits inits) {
        this(MdTrash.class, metadata, inits);
    }

    public QMdTrash(Class<? extends MdTrash> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.fcc.PureSync.context.member.entity.QMember(forProperty("member")) : null;
    }

}

