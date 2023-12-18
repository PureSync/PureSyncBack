package com.fcc.PureSync.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Table(name = "tb_comment")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cmtSeq;
    private String cmtContents;
    @Builder.Default
    private LocalDateTime cmtWdate=LocalDateTime.now();
    @Builder.Default
    private Integer cmtStatus=1;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_seq")
    private Member member;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_seq")
    private Board board;



}