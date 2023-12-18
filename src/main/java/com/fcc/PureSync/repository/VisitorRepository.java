package com.fcc.PureSync.repository;

import com.fcc.PureSync.entity.Visitor;
import com.fcc.PureSync.vo.CountInfoNativeVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface VisitorRepository extends JpaRepository <Visitor, Long> {
    Visitor findByVisitorDateAndVisitorIp(String visitorDate, String visitorIp);

    @Query(value="WITH RECURSIVE DateRange AS (" +
            "SELECT CAST(:startDate AS DATE) AS date" +
            " UNION ALL" +
            " SELECT date - INTERVAL 1 DAY" +
            " FROM DateRange" +
            " WHERE date > CAST(:startDate AS DATE) - INTERVAL 6 DAY" +
            ")" +
            " select DateRange.date, count(*) AS count" +
            " FROM" +
            " DateRange" +
            " LEFT JOIN tb_visitor as vi on DateRange.date = vi.visitor_date" +
            " GROUP BY" +
            " DateRange.date" +
            " ORDER BY" +
            " DateRange.date", nativeQuery = true)
    List<CountInfoNativeVo> getWeeklyVisitor(@Param("startDate") String startDate);

    Integer countByVisitorDate(String startDate);


}

