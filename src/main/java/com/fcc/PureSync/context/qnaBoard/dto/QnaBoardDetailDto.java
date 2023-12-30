package com.fcc.PureSync.context.qnaBoard.dto;

import com.fcc.PureSync.context.qnaBoard.entity.QnaBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class QnaBoardDetailDto {
    private Long qnaBoardSeq;
    private String qnaBoardName;
    private String qnaBoardContents;
    private LocalDateTime qnaBoardWdate;
    private Long memSeq;
    private String memId;
    private Integer qnaBoardStatus;

    public static QnaBoardDetailDto toDto(QnaBoard qnaBoard) {
        return QnaBoardDetailDto.builder()
                .qnaBoardSeq(qnaBoard.getQnaBoardSeq())
                .qnaBoardName(qnaBoard.getQnaBoardName())
                .qnaBoardContents(qnaBoard.getQnaBoardContents())
                .qnaBoardWdate(qnaBoard.getQnaBoardWdate())
                .memSeq(qnaBoard.getMember().getMemSeq())
                .memId(qnaBoard.getMember().getMemId())
                .qnaBoardStatus(qnaBoard.getQnaBoardStatus())
                .build();
    }
}