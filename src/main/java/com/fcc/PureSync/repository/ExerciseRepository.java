package com.fcc.PureSync.repository;

import com.fcc.PureSync.entity.Exercise;
import com.fcc.PureSync.vo.DashboardDefaultNativeVo;
import com.fcc.PureSync.vo.ExerciseStatsNativeVo;
import com.fcc.PureSync.vo.MenuStatsNativeVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
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
                    "   COALESCE(SUM(el.el_time), 0) AS totalTime, " +
                    "   COALESCE(ROUND(SUM(calc.ec_calc * (3.5 * bd.body_weight * el.el_time) / 1000 * 5), 2), 0) as totalKcal " +
                    "FROM " +
                    "   DateRange " +
                    "LEFT JOIN tb_exercise_list AS el ON DateRange.date = DATE(el.el_date) AND el.mem_seq = :memSeq " +
                    "LEFT JOIN tb_body as bd ON el.mem_seq = bd.mem_seq " +
                    "LEFT JOIN tb_exercise_calc as calc ON el.ec_seq = calc.ec_seq " +
                    "GROUP BY " +
                    "   DateRange.date " +
                    "ORDER BY " +
                    "   DateRange.date", nativeQuery = true)
    List<ExerciseStatsNativeVo> findLastDaysExerciseStats(@Param("memSeq") Long memSeq, @Param("startDate") String startDate, @Param("dayInterval") int dayInterval);

    @Query(value =
            "WITH RECURSIVE DateRange AS ( " +
                    "   SELECT CAST(:startDate AS DATE) AS date " +
                    "   UNION ALL " +
                    "   SELECT date - INTERVAL 1 MONTH " +
                    "   FROM DateRange " +
                    "   WHERE date > CAST(:startDate AS DATE) - INTERVAL :monthInterval MONTH " +
                    ") " +
                    "SELECT " +
                    "   DATE_FORMAT(DateRange.date, '%Y-%m') AS date, " +
                    "   COALESCE(SUM(el.el_time), 0) AS totalTime, " +
                    "   COALESCE(ROUND(SUM(calc.ec_calc * (3.5 * bd.body_weight * el.el_time) / 1000 * 5), 2), 0) as totalKcal " +
                    "FROM " +
                    "   DateRange " +
                    "LEFT JOIN tb_exercise_list AS el ON DATE_FORMAT(DateRange.date, '%Y-%m') = DATE_FORMAT(el.el_date, '%Y-%m') AND el.mem_seq = :memSeq " +
                    "LEFT JOIN tb_body as bd ON el.mem_seq = bd.mem_seq " +
                    "LEFT JOIN tb_exercise_calc as calc ON el.ec_seq = calc.ec_seq " +
                    "GROUP BY " +
                    "   DATE_FORMAT(DateRange.date, '%Y-%m') " +
                    "ORDER BY " +
                    "   DATE_FORMAT(DateRange.date, '%Y-%m')", nativeQuery = true)
    List<ExerciseStatsNativeVo> findLastMonthsExerciseStats(@Param("memSeq") Long memSeq, @Param("startDate") String startDate, @Param("monthInterval") int monthInterval);

    @Query(value =
            "SELECT " +
                    "tb1.pv_contents as pvContents, " +
                    "tb1.pv_talker as pvTalker , " +
                    "tb2.body_wish_conscal as wishEatKcal, " +
                    "tb2.body_wish_burncal as wishBurnKcal, " +
                    "tb3.eat_kcal as EatKcal, " +
                    "tb4.burn_kcal as BurnKcal ," +
                    "CASE " +
                    "   WHEN tb2.body_wish_conscal - tb3.eat_kcal > 0 THEN 0 " +
                    "   WHEN tb2.body_wish_conscal - tb3.eat_kcal = 0 THEN 1 " +
                    "   ELSE 2 " +
                    "END + CASE " +
                    "   WHEN tb2.body_wish_burncal - tb4.burn_kcal > 0 THEN 0 " +
                    "   WHEN tb2.body_wish_burncal - tb4.burn_kcal = 0 THEN 1 " +
                    "   ELSE 2 " +
                    "   END + tb5.emotion AS score " +
                    "FROM " +
                    "(SELECT pv_contents, pv_talker FROM tb_positive ORDER BY RAND() LIMIT 1) AS tb1, "+
                    "(SELECT body_wish_conscal, body_wish_burncal FROM tb_body WHERE mem_seq = :memSeq order by body_seq limit 1) AS tb2, " +
                    "(SELECT " +
                    "COALESCE(ROUND(SUM(ml.menu_gram/100 * food.food_kcal), 2), 0) as eat_kcal " +
                    "FROM " +
                    "tb_menu_list ml " +
                    "JOIN tb_food food on ml.food_seq = food.food_seq " +
                    "     WHERE " +
                    "        ml.menu_date = :targetDate and ml.mem_seq = :memSeq) AS tb3, " +
                    "    (SELECT " +
                    "        COALESCE(ROUND(SUM(ec.ec_calc * (3.5 * bd.body_weight * el.el_time) / 1000 * 5), 2), 0) AS burn_kcal " +
                    "     FROM " +
                    "        tb_exercise_list el " +
                    "        JOIN tb_exercise_calc AS ec on ec.ec_seq = el.ec_seq " +
                    "        JOIN tb_body AS bd on bd.mem_seq = el.mem_seq " +
                    "     WHERE " +
                    "        el.el_date = :targetDate and el.mem_seq = :memSeq) AS tb4, " +
                    "(SELECT " +
                    "IFNULL(emo.emo_field, 0) AS emotion " +
                    "FROM tb_md_diary md " +
                    "JOIN tb_emotion emo on md.emo_seq = emo.emo_seq " +
                    "WHERE md.mem_seq = :memSeq and md.dy_status = 1 and md.dy_date = :targetDate " +
                    "union all " +
                    "select 0 AS emotion from dual limit 1) AS tb5 "
            , nativeQuery = true)
    Optional<DashboardDefaultNativeVo> findDefaultData(@Param("memSeq") Long memSeq, @Param("targetDate") String targetDate);

    
}