package com.fcc.PureSync.repository;

import com.fcc.PureSync.dto.AdminMemberDto;
import com.fcc.PureSync.dto.MemberDetailDto;
import com.fcc.PureSync.dto.QAdminMemberDto;
import com.fcc.PureSync.dto.QMemberDetailDto;
import com.fcc.PureSync.entity.MemberSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.fcc.PureSync.entity.QBody.body;
import static com.fcc.PureSync.entity.QMember.member;
import static com.fcc.PureSync.util.Status.mapStatusList;
import static org.apache.logging.log4j.util.Strings.isEmpty;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom  {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<AdminMemberDto> searchMemberList(MemberSearchCondition condition, Pageable pageable) {
        List<AdminMemberDto> content = queryFactory
                .select(new QAdminMemberDto(
                        member.memSeq
                        ,member.memId
                        ,member.memNick
                        ,member.memEmail
                        ,member.memCreatedAt
                        ,new CaseBuilder()
                            .when(member.memStatus.eq(0)).then("비활성화")
                            .when(member.memStatus.eq(1)).then("휴면")
                            .when(member.memStatus.eq(2)).then("탈퇴")
                            .when(member.memStatus.eq(3)).then("정지")
                            .when(member.memStatus.eq(4)).then("정상")
                            .otherwise("알 수 없음")
                ))
                .from(member)
                .where(
                        member.memStatus.ne(5)
                        ,keywordContains(condition.getCategory(), condition.getKeyword())
                        ,roleIsAll(condition.getStatus())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(member.memSeq.asc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(member.count())
                .from(member)
                .where(
                        member.memStatus.ne(5)
                        ,keywordContains(condition.getCategory(), condition.getKeyword())
                        ,roleIsAll(condition.getStatus())
                )
                .orderBy(member.memSeq.asc());

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);

    }

    // keyword 검색조건
    private BooleanExpression keywordContains(String category, String keyword) {
        if (isEmpty(category) || isEmpty(keyword)) {
            return null;
        }

        switch (category) {
            case "id":
                return member.memId.contains(keyword);
            case "nick":
                return member.memNick.contains(keyword);
            case "email":
                return member.memEmail.contains(keyword);
            default:
                return null;
        }
    }

    // role 검색조건
    private BooleanExpression roleIsAll(String roleStr) {
        if (roleStr == null) {
            return null;
        }

        if (roleStr.equals("all")) {
            return null;
        }

        return member.memStatus.in(mapStatusList(roleStr));
    }

    @Override
    public MemberDetailDto getMemberDetail(Long seq) {
        MemberDetailDto result = queryFactory
                .select(new QMemberDetailDto(
                        member.memSeq
                        ,member.memId
                        ,member.memNick
                        ,member.memEmail
                        ,new CaseBuilder()
                            .when(member.memStatus.eq(0)).then("비활성화")
                            .when(member.memStatus.eq(1)).then("휴면")
                            .when(member.memStatus.eq(2)).then("탈퇴")
                            .when(member.memStatus.eq(3)).then("정지")
                            .when(member.memStatus.eq(4)).then("정상")
                            .when(member.memStatus.eq(5)).then("어드민")
                            .otherwise("알 수 없음")
                        ,member.memBirth
                        ,new CaseBuilder()
                            .when(member.memGender.eq("M")).then("남")
                            .when(member.memGender.eq("W")).then("여")
                            .otherwise("알 수 없음")
                        ,member.memImg
                        ,member.memCreatedAt
                        ,member.memLastLoginAt
                        ,body.bodyHeight
                        ,body.bodyWeight
                        ,body.bodyWishWeight
                        ,body.bodyWishConscal
                        ,body.bodyWishBurncal
                        ,body.bodyWdate
                ))
                .from(member)
                .leftJoin(body).on(member.memSeq.eq(body.memSeq))
                .where(member.memSeq.eq(seq))
                .orderBy(body.bodySeq.desc())
                .limit(1)
                .fetchOne();

        return result;
    }
}