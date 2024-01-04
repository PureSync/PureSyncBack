package com.fcc.PureSync.context.exercise.dao;

import com.fcc.PureSync.context.exercise.dto.ExerciseDto;
import jakarta.annotation.Resource;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExerciseDao {

    @Resource(name="sm")
    SqlSessionTemplate sm;

    public List<ExerciseDto> getExerciseList( ExerciseDto exerciseTo ) {
        return  sm.selectList("getExerciseAllList", exerciseTo );
    }

    public List<ExerciseDto> getExerciseTotal( ExerciseDto exerciseTo ) {
        return  sm.selectList("getExerciseTotal", exerciseTo );
    }

}
