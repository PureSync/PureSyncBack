package com.fcc.PureSync.context.qnaBoard.entity;

import com.fcc.PureSync.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_qna_board")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qnaBoardSeq;
    private String qnaBoardName;
    private String qnaBoardContents;
    @Builder.Default
    private LocalDateTime qnaBoardWdate = LocalDateTime.now();
    @Builder.Default
    private Integer qnaBoardStatus = 1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mem_seq")
    private Member member;

    @OneToMany(mappedBy = "qnaBoard", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<QnaBoardFile> qnaBoardFile;

    @OneToMany(mappedBy = "qnaBoard", cascade = CascadeType.ALL)
    private List<QnaComment> qnaComments;
}