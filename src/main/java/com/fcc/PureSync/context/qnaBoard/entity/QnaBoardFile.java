package com.fcc.PureSync.context.qnaBoard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_qna_board_file")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaBoardFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qnaBoardFileSeq;
    private String qnaBoardFileName;
    private Long qnaBoardFileSize;
    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_board_seq", nullable = false)
    private QnaBoard qnaBoard;


}
