package com.fcc.PureSync.repository;

import com.fcc.PureSync.entity.ExerciseList;
import com.fcc.PureSync.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExerciseListRepository extends JpaRepository<ExerciseList, Long> {
    @Query(value = "SELECT * FROM tb_exercise_calc where ec_name like %:param%  ", nativeQuery = true)
    List<ExerciseList> findAllExercise(@Param("param") String exerciseName );
}
