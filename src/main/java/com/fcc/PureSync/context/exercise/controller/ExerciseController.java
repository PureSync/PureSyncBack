package com.fcc.PureSync.context.exercise.controller;

import com.fcc.PureSync.context.exercise.dto.ExerciseDto;
import com.fcc.PureSync.context.exercise.dto.ExerciseResponseDto;

import com.fcc.PureSync.core.ResultDto;
import com.fcc.PureSync.core.jwt.CustomUserDetails;
import com.fcc.PureSync.core.exception.CustomException;
import com.fcc.PureSync.core.exception.CustomExceptionCode;

import com.fcc.PureSync.context.exercise.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
        if (customUserDetails == null) {
            throw new CustomException(CustomExceptionCode.INVALID_JWT);
        }
        Long memSeq = customUserDetails.getMemSeq();
        return exerciseService.getExerciseAllList(exerciseTo, memSeq);
    }

    @PostMapping("/save")
    public  ResultDto exerciseInsert (@RequestBody ExerciseResponseDto exercise, @AuthenticationPrincipal CustomUserDetails customUserDetails ) {
        if (customUserDetails == null) {
            throw new CustomException(CustomExceptionCode.INVALID_JWT);
        }
        Long memSeq = customUserDetails.getMemSeq();
        return exerciseService.insertExercise(exercise, memSeq);
    }

//    @PutMapping ("/update")
//    public  ResultDto exerciseUpdate( @RequestBody Exercise exercise, @AuthenticationPrincipal CustomUserDetails customUserDetails ) {
//        return exerciseService.updateExercise(exercise);
//    }

    @DeleteMapping("/delete")
    public  ResultDto exerciseDelete (@RequestBody ExerciseResponseDto exercise, @AuthenticationPrincipal CustomUserDetails customUserDetails ) {
        if (customUserDetails == null) {
            throw new CustomException(CustomExceptionCode.INVALID_JWT);
        }
        Long memSeq = customUserDetails.getMemSeq();
        return exerciseService.deleteExercise(exercise, memSeq);
    }

}
