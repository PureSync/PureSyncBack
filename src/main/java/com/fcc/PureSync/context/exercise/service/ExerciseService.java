package com.fcc.PureSync.context.exercise.service;


import com.fcc.PureSync.context.exercise.dao.ExerciseDao;
import com.fcc.PureSync.context.exercise.dto.ExerciseDto;
import com.fcc.PureSync.context.exercise.dto.ExerciseResponseDto;
import com.fcc.PureSync.common.ResultDto;
import com.fcc.PureSync.context.exercise.entity.Exercise;
import com.fcc.PureSync.context.exercise.entity.ExerciseList;

import com.fcc.PureSync.entity.Member;
import com.fcc.PureSync.exception.CustomException;
import com.fcc.PureSync.exception.CustomExceptionCode;
import com.fcc.PureSync.context.exercise.repository.ExerciseListRepository;
import com.fcc.PureSync.context.exercise.repository.ExerciseRepository;
import com.fcc.PureSync.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ExerciseService {

    private final ExerciseDao exerciseDao;
    private final ExerciseRepository exerciseRepository;
    private final ExerciseListRepository exerciseListRepository;
    private final MemberRepository memberRepository;


    // 전체 데이터
    public ResultDto getAllExerciseList( String exerciseName ) {
        try {
            List<ExerciseList> allExercise = exerciseListRepository.findAllExercise(exerciseName);
            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("allExercise", allExercise);
            ResultDto resultDto =  ResultDto.of(HttpStatus.OK.value(), HttpStatus.OK, "성공", data);
            return resultDto;
        } catch (CustomException e) {
            throw new CustomException(CustomExceptionCode.NOT_FOUND_EXERCISE);
        }
    }

    // 리스트
    public ResultDto getExerciseAllList(ExerciseDto exerciseTo , Long memSeq) {
        exerciseTo.setMem_seq(memSeq);
        List<ExerciseDto> exerciseList = exerciseDao.getExerciseList(exerciseTo);

        try {
            HashMap<String, Object> data = new HashMap<>();
            data.put("exerciseList", exerciseList);
            String msg = "운동 불러오기에 성공했습니다.";
            return ResultDto.of(HttpStatus.OK.value(), HttpStatus.OK, msg, data);
        } catch ( CustomException e ) {
            throw new CustomException( CustomExceptionCode.USER_ROLE_NOT_EXIST );     // 권한X
        }
    }


    public ResultDto insertExercise(ExerciseResponseDto exerciseResponseDto , Long memSeq ) {
        Member member = memberRepository.findByMemSeq(memSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));

        ExerciseList exerciseInfo = exerciseListRepository.findByEcSeq(exerciseResponseDto.getEcSeq())
                .orElseThrow(() -> new CustomException(CustomExceptionCode.ALREADY_DELETED_ELEMENT));

        Exercise exercise = Exercise.builder()
                .elDate(exerciseResponseDto.getElDate())
                .elTime(exerciseResponseDto.getElTime())
                .exerciseList(exerciseInfo)
                .member(member)
                .build();


        return performExerciseOperation( exercise, "운동 입력에 성공했습니다.", CustomExceptionCode.INSERT_FAIL );
    }

//    @Transactional
//    public ResultDto updateExercise( Exercise exercise ) {
//        return performExerciseOperation( exercise, "운동 수정에 성공하였습니다.", CustomExceptionCode.UPDATE_FAIL );
//    }


    public ResultDto deleteExercise( ExerciseResponseDto exerciseResponseDto, Long memSeq ) {
        Member member = memberRepository.findByMemSeq(memSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));

        Exercise exercise = Exercise.builder()
                .elSeq(exerciseResponseDto.getElSeq())
                .member(member)
                .build();

        return performExerciseOperation( exercise, "운동 삭제에 성공하였습니다.", CustomExceptionCode.DELETE_FAIL );
    }


    private ResultDto performExerciseOperation( Exercise exercise, String successMessage, CustomExceptionCode exceptionCode ) {

        try {
            if ( exceptionCode == CustomExceptionCode.DELETE_FAIL ) {
                // 삭제 작업 수행
                exerciseRepository.delete( exercise );
            } else {
                // 추가 또는 수정 작업 수행
                exerciseRepository.save( exercise );
            }

            HashMap<String, Object> map = new HashMap<>();
            map.put( "exercise", exercise );

            return ResultDto.of(HttpStatus.OK.value(), HttpStatus.OK, "성공", map);

        } catch (CustomException e) {
            throw new CustomException( exceptionCode );
        }
    }

}
