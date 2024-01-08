package com.fcc.PureSync.context.exercise.dto;

import com.fcc.PureSync.context.exercise.entity.Exercise;
import com.fcc.PureSync.context.menu.entity.Menu;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseResponseDto {
    private Long elSeq;
    private String elDate;
    private Integer elTime;
    @Builder.Default
    private LocalDateTime elWdate = LocalDateTime.now();
    private Long memSeq;
    private Long ecSeq;

    public static ExerciseResponseDto toDto(Exercise exercise) {
        return ExerciseResponseDto.builder()
                .ecSeq(exercise.getExerciseList().getEcSeq())
                .memSeq(exercise.getMember().getMemSeq())
                .elDate(exercise.getElDate())
                .elTime(exercise.getElTime())
                .elWdate(exercise.getElWdate())
                .elSeq(exercise.getElSeq())
                .build();
    }

}
