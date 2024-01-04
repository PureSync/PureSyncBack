package com.fcc.PureSync.context.menu.repository;

import com.fcc.PureSync.context.menu.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {
    @Query(value = "SELECT * FROM tb_food where food_name like %:param%  ", nativeQuery = true)
    List<Food> findAllFood(@Param("param") String foodName);
    Optional<Food> findByFoodSeq(Long foodSeq);


}
