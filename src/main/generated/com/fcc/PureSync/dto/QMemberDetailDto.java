package com.fcc.PureSync.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.fcc.PureSync.dto.QMemberDetailDto is a Querydsl Projection type for MemberDetailDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMemberDetailDto extends ConstructorExpression<MemberDetailDto> {

    private static final long serialVersionUID = -846759937L;

    public QMemberDetailDto(com.querydsl.core.types.Expression<Long> memSeq, com.querydsl.core.types.Expression<String> memId, com.querydsl.core.types.Expression<String> memNick, com.querydsl.core.types.Expression<String> memEmail, com.querydsl.core.types.Expression<String> status, com.querydsl.core.types.Expression<String> memBirth, com.querydsl.core.types.Expression<String> memGender, com.querydsl.core.types.Expression<String> memImg, com.querydsl.core.types.Expression<java.time.LocalDateTime> memCreateAt, com.querydsl.core.types.Expression<java.time.LocalDateTime> memLastLoginAt, com.querydsl.core.types.Expression<Double> bodyHeight, com.querydsl.core.types.Expression<Double> bodyWeight, com.querydsl.core.types.Expression<Double> bodyWishWeight, com.querydsl.core.types.Expression<Double> bodyWishConsCal, com.querydsl.core.types.Expression<Double> bodyWishBurnCal, com.querydsl.core.types.Expression<java.time.LocalDateTime> bodyWdate) {
        super(MemberDetailDto.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class, double.class, double.class, double.class, double.class, double.class, java.time.LocalDateTime.class}, memSeq, memId, memNick, memEmail, status, memBirth, memGender, memImg, memCreateAt, memLastLoginAt, bodyHeight, bodyWeight, bodyWishWeight, bodyWishConsCal, bodyWishBurnCal, bodyWdate);
    }

}

