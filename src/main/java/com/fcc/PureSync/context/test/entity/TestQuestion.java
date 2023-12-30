package com.fcc.PureSync.context.test.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_test_question")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long queSeq;
    private Long queNum;
    private String queContents;
    private Long testSeq;



}
