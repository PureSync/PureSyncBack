package com.fcc.PureSync.repository;

import com.fcc.PureSync.entity.Menu;
import com.fcc.PureSync.vo.MenuStatsNativeVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    @Query(value =
            "WITH RECURSIVE DateRange AS (" +
                    "  SELECT CAST(:startDate AS DATE) AS date " +
                    "  UNION ALL " +
                    "  SELECT date - INTERVAL 1 DAY " +
                    "  FROM DateRange " +
                    "  WHERE date > CAST(:startDate AS DATE) - INTERVAL 6 DAY " +
                    ") " +
                    "SELECT " +
                    "   DateRange.date, " +
                    "   SUM(CASE WHEN ml.menu_when = 1 THEN ROUND(ml.menu_gram / 100 * food.food_kcal) ELSE 0 END) AS kcalBreakfast, " +
                    "   SUM(CASE WHEN ml.menu_when = 2 THEN ROUND(ml.menu_gram / 100 * food.food_kcal) ELSE 0 END) AS kcalLunch, " +
                    "   SUM(CASE WHEN ml.menu_when = 3 THEN ROUND(ml.menu_gram / 100 * food.food_kcal) ELSE 0 END) AS kcalDinner, " +
                    "   SUM(CASE WHEN ml.menu_when = 4 THEN ROUND(ml.menu_gram / 100 * food.food_kcal) ELSE 0 END) AS kcalSnack " +
//                    "   ROUND(SUM(food.food_car), 2) as foodCar, " +
//                    "   ROUND(SUM(food.food_pro), 2) as foodPro, " +
//                    "   ROUND(SUM(food.food_fat), 2) as foodFat, " +
//                    "   ROUND(SUM(food.food_sugar), 2) as foodSugar, " +
//                    "   ROUND(SUM(food.food_na), 2) as foodNa, " +
//                    "   ROUND(SUM(food.food_col), 2) as foodCol " +
                    "FROM " +
                    "  DateRange " +
                    "LEFT JOIN tb_menu_list AS ml ON DateRange.date = ml.menu_date AND ml.mem_seq = :memSeq " +
                    "LEFT JOIN tb_food AS food ON ml.food_seq = food.food_seq " +
                    "GROUP BY " +
                    "  DateRange.date " +
                    "ORDER BY " +
                    "  DateRange.date "
            , nativeQuery = true)
    List<MenuStatsNativeVo> find7DaysMenuStats(@Param("memSeq") Long memSeq, @Param("startDate") String startDate);
}
