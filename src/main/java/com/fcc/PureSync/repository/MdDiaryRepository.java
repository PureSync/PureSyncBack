package com.fcc.PureSync.repository;

import com.fcc.PureSync.entity.MdDiary;
import com.fcc.PureSync.entity.Member;
import com.fcc.PureSync.vo.EmotionNativeVo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface MdDiaryRepository extends JpaRepository <MdDiary, Long> {
    List<MdDiary> findAllByMemberOrderByDyDateDescDyWdateDesc(Member member, Pageable pageable);


    @Query(value =
            "WITH RECURSIVE DateRange AS ( " +
            "  SELECT CAST(DATE_FORMAT(:targetDate, '%Y-%m-01') AS DATE) AS date " +
            "  UNION ALL " +
            "  SELECT date + INTERVAL 1 DAY " +
            "  FROM DateRange " +
            "  WHERE date < LAST_DAY(:targetDate) " +
            ") " +
            "SELECT " +
            "   DateRange.date AS date, " +
            "   COALESCE(emo.emo_field, 0) AS emoCode " +
            "FROM " +
            "  DateRange " +
            "LEFT JOIN tb_md_diary AS md ON DateRange.date = DATE(md.dy_date) AND md.mem_seq = :memSeq " +
            "LEFT JOIN tb_emotion AS emo ON md.emo_seq = emo.emo_seq " +
            "GROUP BY " +
            "  DateRange.date " +
            "ORDER BY " +
            "  DateRange.date", nativeQuery = true)
    List<EmotionNativeVo> findDataByMonth(@Param("memSeq") Long memSeq, @Param("targetDate") String targetDate);
}
