package com.fcc.PureSync.context.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = 1134364808L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoard board = new QBoard("board");

    public final StringPath boardContents = createString("boardContents");

    public final ListPath<BoardFile, QBoardFile> boardFile = this.<BoardFile, QBoardFile>createList("boardFile", BoardFile.class, QBoardFile.class, PathInits.DIRECT2);

    public final NumberPath<Long> boardLikescount = createNumber("boardLikescount", Long.class);

    public final StringPath boardName = createString("boardName");

    public final NumberPath<Long> boardSeq = createNumber("boardSeq", Long.class);

    public final NumberPath<Integer> boardStatus = createNumber("boardStatus", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> boardWdate = createDateTime("boardWdate", java.time.LocalDateTime.class);

    public final ListPath<Comment, QComment> comments = this.<Comment, QComment>createList("comments", Comment.class, QComment.class, PathInits.DIRECT2);

    public final com.fcc.PureSync.context.member.entity.QMember member;

    public QBoard(String variable) {
        this(Board.class, forVariable(variable), INITS);
    }

    public QBoard(Path<? extends Board> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoard(PathMetadata metadata, PathInits inits) {
        this(Board.class, metadata, inits);
    }

    public QBoard(Class<? extends Board> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.fcc.PureSync.context.member.entity.QMember(forProperty("member")) : null;
    }

}

