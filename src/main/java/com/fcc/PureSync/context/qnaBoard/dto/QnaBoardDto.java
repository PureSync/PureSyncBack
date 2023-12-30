package com.fcc.PureSync.context.qnaBoard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fcc.PureSync.context.qnaBoard.entity.QnaBoard;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class QnaBoardDto {
    private Long qnaBoardSeq;
    private String qnaBoardName;
    private String qnaBoardContents;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime qnaBoardWdate;
    private Long memSeq;
    private String memId;
    private Integer qnaBoardStatus;
    private List<QnaBoardFileDto> qnaBoardFile;
    private List<QnaCommentDto> qnaComment;

    //@Builder.Default 시도해보기
    public static QnaBoardDto toDto(QnaBoard qnaBoard) {
        return QnaBoardDto.builder()
                .qnaBoardSeq(qnaBoard.getQnaBoardSeq())
                .qnaBoardName(qnaBoard.getQnaBoardName())
                .qnaBoardContents(qnaBoard.getQnaBoardContents())
                .qnaBoardWdate(qnaBoard.getQnaBoardWdate())
                .memSeq(qnaBoard.getMember().getMemSeq())
                .qnaBoardStatus(qnaBoard.getQnaBoardStatus())
                .build();
    }

    public static QnaBoardDto QnaBoardDetailDto(QnaBoard qnaBoard) {
        List<QnaCommentDto> qnaCommentDtoList = qnaBoard.getQnaComments().stream()
                .map(QnaCommentDto::toDto)
                .sorted(Comparator.comparing(QnaCommentDto::getQnaCmtWdate).reversed())
                .toList();
        List<QnaBoardFileDto> qnaBoardFileDtoList = qnaBoard.getQnaBoardFile().stream()
                .map(QnaBoardFileDto::toDto)
                .toList();
        return QnaBoardDto.builder()
                .qnaBoardSeq(qnaBoard.getQnaBoardSeq())
                .qnaBoardName(qnaBoard.getQnaBoardName())
                .qnaBoardContents(qnaBoard.getQnaBoardContents())
                .qnaBoardWdate(qnaBoard.getQnaBoardWdate())
                .memSeq(qnaBoard.getMember().getMemSeq())
                .memId(qnaBoard.getMember().getMemId())
                .qnaBoardStatus(qnaBoard.getQnaBoardStatus())
                .qnaBoardFile(qnaBoardFileDtoList)
                .qnaComment(qnaCommentDtoList)
                .build();
    }

    public static QnaBoardDto QnaBoardAllDetailDto(QnaBoard qnaBoard) {
        return QnaBoardDto.builder()
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