package com.fcc.PureSync.controller;

import com.fcc.PureSync.dto.ExerciseDto;
import com.fcc.PureSync.dto.ExerciseResponseDto;
import com.fcc.PureSync.dto.ResultDto;
import com.fcc.PureSync.entity.Exercise;
import com.fcc.PureSync.jwt.CustomUserDetails;
import com.fcc.PureSync.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResultDto getAllExerciseList ( ExerciseDto exerciseTo, @AuthenticationPrincipal CustomUserDetails customUserDetails ) {
        Long memSeq = customUserDetails.getMemSeq();
        return exerciseService.getExerciseAllList(exerciseTo, memSeq);
    }

    @PostMapping("/save")
    public  ResultDto exerciseInsert (@RequestBody ExerciseResponseDto exercise, @AuthenticationPrincipal CustomUserDetails customUserDetails ) {
        Long memSeq = customUserDetails.getMemSeq();
        return exerciseService.insertExercise(exercise, memSeq);
    }

//    @PutMapping ("/update")
//    public  ResultDto exerciseUpdate( @RequestBody Exercise exercise, @AuthenticationPrincipal CustomUserDetails customUserDetails ) {
//        return exerciseService.updateExercise(exercise);
//    }

    @PostMapping("/delete")
    public  ResultDto exerciseDelete (@RequestBody ExerciseResponseDto exercise, @AuthenticationPrincipal CustomUserDetails customUserDetails ) {
        Long memSeq = customUserDetails.getMemSeq();
        return exerciseService.deleteExercise(exercise, memSeq);
    }

}
