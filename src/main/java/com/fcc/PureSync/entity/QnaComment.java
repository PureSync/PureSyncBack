package com.fcc.PureSync.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_qna_comment")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qnaCmtSeq;
    private String qnaCmtContents;
    @Builder.Default
    private LocalDateTime qnaCmtWdate = LocalDateTime.now();
    private String qnaCmtWriter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_board_seq")
    private QnaBoard qnaBoard;
}