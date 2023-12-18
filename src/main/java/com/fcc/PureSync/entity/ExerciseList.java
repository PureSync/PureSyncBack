package com.fcc.PureSync.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
//매개변수가 없는 생성자를 PROTECED  접근권한으로 만들어라  == 직접객체생성 하지 마라
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name="tb_exercise_calc")
public class ExerciseList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ecSeq;

    private String ecName;
    private Double ecCalc;
}
