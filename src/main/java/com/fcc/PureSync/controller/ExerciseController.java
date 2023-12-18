package com.fcc.PureSync.controller;

import com.fcc.PureSync.dto.ExerciseDto;
import com.fcc.PureSync.dto.ResultDto;
import com.fcc.PureSync.entity.Exercise;
import com.fcc.PureSync.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping( value = "/api/exercise" )
@RestController
public class ExerciseController {
    @Autowired
    private ExerciseService exerciseService;

    @GetMapping("/exerciseList")
    public ResultDto getAllExerciseList(String exerciseName) {
        return exerciseService.getAllExerciseList(exerciseName);
    }

    @GetMapping("/list")
    public ResultDto getAllExerciseList ( ExerciseDto exerciseTo ) {
        return exerciseService.getExerciseAllList(exerciseTo);
    }

    @PostMapping("/save")
    public  ResultDto exerciseInsert ( @RequestBody Exercise exercise ) {
        return exerciseService.insertExercise(exercise);
    }

    @PutMapping ("/update")
    public  ResultDto exerciseUpdate( @RequestBody Exercise exercise ) {
        return exerciseService.updateExercise(exercise);
    }

    @PostMapping("/delete")
    public  ResultDto exerciseDelete (@RequestBody Exercise exercise ) {
        return exerciseService.deleteExercise(exercise);
    }

}
