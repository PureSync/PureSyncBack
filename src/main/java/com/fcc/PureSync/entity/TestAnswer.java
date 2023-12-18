package com.fcc.PureSync.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_test_answer")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ansSeq;
    private int testSeq;
    private String testAns;
    private int total;

    @Builder.Default
    private LocalDateTime ansWdate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_seq")
    private Member member;
}
