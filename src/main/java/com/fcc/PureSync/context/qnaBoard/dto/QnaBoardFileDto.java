package com.fcc.PureSync.context.qnaBoard.dto;

import com.fcc.PureSync.context.qnaBoard.entity.QnaBoardFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QnaBoardFileDto {
    private Long qnaBoardFileSeq;
    private String qnaBoardFileName;
    private Long qnaBoardFileSize;
    private Long qnaBoardSeq;
    private String fileUrl;
    public static QnaBoardFileDto toDto(QnaBoardFile qnaBoardFile) {
        return QnaBoardFileDto.builder()
                .qnaBoardFileSeq(qnaBoardFile.getQnaBoardFileSeq())
                .qnaBoardFileName(qnaBoardFile.getQnaBoardFileName())
                .qnaBoardFileSize(qnaBoardFile.getQnaBoardFileSize())
                .qnaBoardSeq(qnaBoardFile.getQnaBoard().getQnaBoardSeq())
                .fileUrl(qnaBoardFile.getFileUrl())
                .build();
    }
}
