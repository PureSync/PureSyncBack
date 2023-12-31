package com.fcc.PureSync.context.exercise.repository;

import com.fcc.PureSync.context.dashboard.vo.DefaultChartVo;
import com.fcc.PureSync.context.dashboard.vo.DashboardDefaultNativeVo;
import com.fcc.PureSync.context.dashboard.vo.ExerciseStatsNativeVo;
import com.fcc.PureSync.context.exercise.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    @Query(value =
            "WITH RECURSIVE DateRange AS ( " +
                    "   SELECT CAST(:targetDate AS DATE) AS date " +
                    "   UNION ALL " +
                    "   SELECT date - INTERVAL 1 DAY " +
                    "   FROM DateRange " +
                    "   WHERE date > :targetDate - INTERVAL 29 DAY " +
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
    List<ExerciseStatsNativeVo> findLastDaysExerciseStats(@Param("memSeq") Long memSeq, @Param("targetDate") String targetDate);

    @Query(value =
            "WITH RECURSIVE DateRange AS ( " +
                    "   SELECT CAST(:targetDate AS DATE) AS date " +
                    "   UNION ALL " +
                    "   SELECT date - INTERVAL 1 MONTH " +
                    "   FROM DateRange " +
                    "   WHERE date > CAST(:targetDate AS DATE) - INTERVAL 11 MONTH " +
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
    List<ExerciseStatsNativeVo> findLastMonthsExerciseStats(@Param("memSeq") Long memSeq, @Param("targetDate") String targetDate);

    @Query(value =
            "WITH RECURSIVE daterange AS ( " +
                    "   SELECT CAST(:targetDate AS DATE) AS date " +
                    "   UNION ALL " +
                    "   SELECT date - INTERVAL 1 DAY " +
                    "   FROM daterange " +
                    "   WHERE date > CAST(:targetDate AS DATE) - INTERVAL 6 DAY " +
                    ") " +
                    "SELECT daterange.date, " +
                    "   Coalesce(sleep.totaltime, 0)    AS sleepTime, " +
                    "   Coalesce(exercise.totaltime, 0) AS exerciseTime, " +
                    "   Coalesce(exercise.totalkcal, 0) AS exerciseKcal, " +
                    "   Coalesce(ml.kcalbreakfast, 0)   AS breakfastKcal, " +
                    "   Coalesce(ml.kcallunch, 0)       AS lunchKcal, " +
                    "   Coalesce(ml.kcaldinner, 0)      AS dinnerKcal, " +
                    "   Coalesce(ml.kcalsnack, 0)       AS snackKcal " +
                    "FROM " +
                    "   daterange " +
                    "LEFT JOIN (SELECT Date(sleep_wudate)                                AS" +
                    "                         sleep_date," +
                    "                         Timestampdiff(minute, sleep_godate, sleep_wudate) AS" +
                    "                         totalTime" +
                    "                  FROM   tb_sleep" +
                    "                  WHERE  mem_seq = :memSeq) AS sleep" +
                    "              ON daterange.date = sleep.sleep_date" +
                    "       LEFT JOIN (SELECT el.el_date," +
                    "                         Sum(el.el_time) AS totalTime," +
                    "                         ( Round(Sum(calc.ec_calc * ( 3.5 * bd.body_weight *" +
                    "                                                    el.el_time ) / 1000" +
                    "                                     * 5), 2)" +
                    "                         )               AS totalKcal" +
                    "                  FROM   tb_exercise_list AS el" +
                    "                         JOIN tb_exercise_calc AS calc" +
                    "                           ON el.ec_seq = calc.ec_seq" +
                    "                         JOIN tb_body AS bd" +
                    "                           ON el.mem_seq = bd.mem_seq" +
                    "                  WHERE  el.mem_seq = :memSeq" +
                    "                  GROUP  BY el.el_date) AS exercise" +
                    "              ON daterange.date = exercise.el_date" +
                    "       LEFT JOIN (SELECT menu_date," +
                    "                         Sum(CASE" +
                    "                               WHEN menu.menu_when = 1 THEN Round(" +
                    "                               menu.menu_gram / 100 * food.food_kcal)" +
                    "                               ELSE 0" +
                    "                             end) AS kcalBreakfast," +
                    "                         Sum(CASE" +
                    "                               WHEN menu.menu_when = 2 THEN Round(" +
                    "                               menu.menu_gram / 100 * food.food_kcal)" +
                    "                               ELSE 0" +
                    "                             end) AS kcalLunch," +
                    "                         Sum(CASE" +
                    "                               WHEN menu.menu_when = 3 THEN Round(" +
                    "                               menu.menu_gram / 100 * food.food_kcal)" +
                    "                               ELSE 0" +
                    "                             end) AS kcalDinner," +
                    "                         Sum(CASE" +
                    "                               WHEN menu.menu_when = 4 THEN Round(" +
                    "                               menu.menu_gram / 100 * food.food_kcal)" +
                    "                               ELSE 0" +
                    "                             end) AS kcalSnack" +
                    "                  FROM   tb_menu_list AS menu" +
                    "                         JOIN tb_food AS food" +
                    "                           ON menu.food_seq = food.food_seq" +
                    "                  WHERE  menu.mem_seq = :memSeq" +
                    "                  GROUP  BY menu.menu_date) AS ml" +
                    "              ON daterange.date = menu_date " +
                    "GROUP BY " +
                    "   daterange.date " +
                    "ORDER BY " +
                    "   daterange.date", nativeQuery = true)
    List<DefaultChartVo> find7DaysChartData(@Param("memSeq") Long memSeq, @Param("targetDate") String targetDate);

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
    DashboardDefaultNativeVo findDefaultData(@Param("memSeq") Long memSeq, @Param("targetDate") String targetDate);

    
}