package com.fcc.PureSync.context.exercise.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
//매개변수가 없는 생성자를 PROTECED  접근권한으로 만들어라  == 직접객체생성 하지 마라
@NoArgsConstructor(access= AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
@Setter
public class ExerciseDto {
    private Long el_seq;
    private String el_date;
    private String el_time;
    private Double el_total;
    private String el_wdate;
    private Long mem_seq;
    private Long ec_seq;

    private String ec_name;
    private Double ec_calc;
    private String mem_gender;
    private Double body_height;
    private Double body_weigh;


}
