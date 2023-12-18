package com.fcc.PureSync.dao;

import com.fcc.PureSync.dto.ExerciseDto;
import com.fcc.PureSync.entity.Exercise;
import jakarta.annotation.Resource;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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
