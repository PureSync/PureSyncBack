package com.fcc.PureSync.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fcc.PureSync.entity.Exercise;
import com.fcc.PureSync.entity.Menu;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
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
