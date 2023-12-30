package com.fcc.PureSync.repository;

import com.fcc.PureSync.entity.Sleep;
import com.fcc.PureSync.context.dashboard.vo.SleepStatsNativeVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SleepRepository extends JpaRepository<Sleep, Long> {

    @Query(value =
            "WITH RECURSIVE DateRange AS ( " +
            "   SELECT CAST(:startDate AS DATE) AS date " +
            "   UNION ALL " +
            "   SELECT date - INTERVAL 1 DAY " +
            "   FROM DateRange " +
            "   WHERE date > :startDate - INTERVAL :dayInterval DAY " +
            ") " +
            "SELECT " +
            "   CAST(DateRange.date AS CHAR) AS date, " +
            "   COALESCE(SUM(TIMESTAMPDIFF(MINUTE, tb_sleep.sleep_godate, tb_sleep.sleep_wudate)), 0) AS totalTime " +
            "FROM " +
            "   DateRange " +
            "LEFT JOIN tb_sleep ON DateRange.date = DATE(tb_sleep.sleep_wudate) AND tb_sleep.mem_seq = :memSeq " +
            "GROUP BY " +
            "   DateRange.date " +
            "ORDER BY " +
            "   DateRange.date", nativeQuery = true)
    List<SleepStatsNativeVo> findLastDaysSleepStats(@Param("memSeq") Long memSeq, @Param("startDate") String startDate, @Param("dayInterval") int dayInterval);

    @Query(value =
            "WITH RECURSIVE DateRange AS ( " +
            "   SELECT CAST(:startDate AS DATE) AS date " +
            "   UNION ALL " +
            "   SELECT date - INTERVAL 1 MONTH " +
            "   FROM DateRange " +
            "   WHERE date > :startDate - INTERVAL :monthInterval MONTH " +
            ") " +
            "SELECT " +
            "   DATE_FORMAT(DateRange.date, '%Y-%m') AS date, " +
            "   COALESCE(SUM(TIMESTAMPDIFF(MINUTE, sl.sleep_godate, sl.sleep_wudate)), 0) AS totalTime " +
            "FROM " +
            "   DateRange " +
            "LEFT JOIN tb_sleep sl ON DATE_FORMAT(DateRange.date, '%Y-%m') = DATE_FORMAT(DATE(sl.sleep_wudate), '%Y-%m') AND sl.mem_seq = :memSeq " +
            "GROUP BY " +
            "   DATE_FORMAT(DateRange.date, '%Y-%m') " +
            "ORDER BY " +
            "   DATE_FORMAT(DateRange.date, '%Y-%m')", nativeQuery = true)
    List<SleepStatsNativeVo> findLastMonthsSleepStats(@Param("memSeq") Long memSeq, @Param("startDate") String startDate, @Param("monthInterval") int monthInterval);


    List<Sleep> findByMember_MemSeq(Long memSeq);
}
