package com.fcc.PureSync.context.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fcc.PureSync.context.board.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long cmtSeq;
    private String cmtContents;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cmtWdate;
    private Integer cmtStatus;
    private Long memSeq;
    private String memId;
    private Long boardSeq;

    public static CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .cmtSeq(comment.getCmtSeq())
                .cmtContents(comment.getCmtContents())
                .cmtWdate(comment.getCmtWdate())
                .cmtStatus(comment.getCmtStatus())
                .memSeq(comment.getMember().getMemSeq())
                .memId(comment.getMember().getMemId())
                .boardSeq(comment.getBoard().getBoardSeq())
                .build();
    }
}