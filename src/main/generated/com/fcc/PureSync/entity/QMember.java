package com.fcc.PureSync.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1064722047L;

    public static final QMember member = new QMember("member1");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath memBirth = createString("memBirth");

    public final DateTimePath<java.time.LocalDateTime> memCreatedAt = createDateTime("memCreatedAt", java.time.LocalDateTime.class);

    public final StringPath memEmail = createString("memEmail");

    public final StringPath memGender = createString("memGender");

    public final StringPath memId = createString("memId");

    public final StringPath memImg = createString("memImg");

    public final DateTimePath<java.time.LocalDateTime> memLastLoginAt = createDateTime("memLastLoginAt", java.time.LocalDateTime.class);

    public final StringPath memNick = createString("memNick");

    public final StringPath memPassword = createString("memPassword");

    public final NumberPath<Long> memSeq = createNumber("memSeq", Long.class);

    public final NumberPath<Integer> memStatus = createNumber("memStatus", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> memUpdatedAt = _super.memUpdatedAt;

    //inherited
    public final StringPath memUpdatedBy = _super.memUpdatedBy;

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

