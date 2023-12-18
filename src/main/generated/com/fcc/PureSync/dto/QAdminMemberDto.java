package com.fcc.PureSync.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.fcc.PureSync.dto.QAdminMemberDto is a Querydsl Projection type for AdminMemberDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAdminMemberDto extends ConstructorExpression<AdminMemberDto> {

    private static final long serialVersionUID = -1314260149L;

    public QAdminMemberDto(com.querydsl.core.types.Expression<Long> memSeq, com.querydsl.core.types.Expression<String> memId, com.querydsl.core.types.Expression<String> memNick, com.querydsl.core.types.Expression<String> memEmail, com.querydsl.core.types.Expression<java.time.LocalDateTime> memCreateAt, com.querydsl.core.types.Expression<String> status) {
        super(AdminMemberDto.class, new Class<?>[]{long.class, String.class, String.class, String.class, java.time.LocalDateTime.class, String.class}, memSeq, memId, memNick, memEmail, memCreateAt, status);
    }

}

