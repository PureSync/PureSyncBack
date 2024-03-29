package com.fcc.PureSync.context.exercise.entity;

import com.fcc.PureSync.context.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
//매개변수가 없는 생성자를 PROTECED  접근권한으로 만들어라  == 직접객체생성 하지 마라
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name="tb_exercise_list")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long elSeq;
    private String elDate;
    private Integer elTime;
    @Builder.Default
    private LocalDateTime elWdate = LocalDateTime.now();


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mem_seq")
    Member member;

    @ManyToOne()
    @JoinColumn(name = "ec_seq")
    ExerciseList exerciseList;


}
