package com.fcc.PureSync.context.member.repository;

import com.fcc.PureSync.context.member.entity.Member;
import com.fcc.PureSync.repository.MemberRepositoryCustom;
import com.fcc.PureSync.vo.CountInfoNativeVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    Optional<Member> findByMemSeq(Long memId);

    Optional<Member> findByMemId(String memId);

    Optional<Member> findByMemIdAndMemStatus(String memId, Integer memStatus);

    Optional<Member> findByMemNick(String memNick);

    Optional<Member> findByMemEmail(String email);

    Member findByMemSeqAndMemStatus(Long memSeq, Integer i);

    Integer countByMemCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    Integer countByMemStatusAndMemUpdatedAtBetween(Integer memStatus, LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "WITH RECURSIVE DateRange AS (" +
            "  SELECT CAST(:startDate AS DATE) AS date" +
            "  UNION ALL" +
            "  SELECT date - INTERVAL 1 DAY" +
            "  FROM DateRange" +
            "  WHERE date > DATE_SUB(CAST(:startDate AS DATE), INTERVAL 6 DAY)" +
            ")" +
            " SELECT DateRange.date, COUNT(tb_member.mem_created_at) as count" +
            " FROM DateRange" +
            " LEFT JOIN tb_member ON DateRange.date = DATE(tb_member.mem_created_at)" +
            " GROUP BY DateRange.date" +
            " ORDER BY DateRange.date", nativeQuery = true)
    List<CountInfoNativeVo> countByMemberCreatedAt(@Param("startDate") LocalDateTime startDate);

    @Query(value = "WITH RECURSIVE DateRange AS (" +
            "  SELECT CAST(:startDate AS DATE) AS date" +
            " UNION ALL" +
            " SELECT date - INTERVAL 1 DAY" +
            " FROM DateRange" +
            " WHERE date > DATE_SUB(CAST(:startDate AS DATE), INTERVAL 6 DAY)" +
            ")" +
            " SELECT DateRange.date, COUNT(tb_member.mem_updated_at) as count" +
            " FROM DateRange" +
            " LEFT JOIN tb_member ON DateRange.date = DATE(tb_member.mem_updated_at) AND tb_member.mem_status = 2" +
            " GROUP BY DateRange.date" +
            " ORDER BY DateRange.date", nativeQuery = true)
    List<CountInfoNativeVo> countByMemberUpdatedAt(@Param("startDate") LocalDateTime startDate);
}