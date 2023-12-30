package com.fcc.PureSync.context.board.dto;

import com.fcc.PureSync.context.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class BoardDetailDto {
    private Long boardSeq;
    private String boardName;
    private String boardContents;
    private LocalDateTime boardWdate;
    private Long boardLikescount;
    private Long memSeq;
    private String memId;
    private Integer boardStatus;

    //@Builder.Default 시도해보기
    public static BoardDetailDto toDto(Board board) {
        return BoardDetailDto.builder()
                .boardSeq(board.getBoardSeq())
                .boardName(board.getBoardName())
                .boardContents(board.getBoardContents())
                .boardWdate(board.getBoardWdate())
                .boardLikescount(board.getBoardLikescount())
                .memSeq(board.getMember().getMemSeq())
                .memId(board.getMember().getMemId())
                .boardStatus(board.getBoardStatus())
                .build();
    }

}