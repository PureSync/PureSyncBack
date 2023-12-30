package com.fcc.PureSync.context.board.dto;

import com.fcc.PureSync.context.board.entity.BoardFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardFileDto {

    private Long boardfileSeq;
    private String boardfileName;
    private Long boardfileSize;
    private Long boardSeq;
    private String fileUrl;
    public static BoardFileDto toDto(BoardFile boardFile) {
        return BoardFileDto.builder()
                .boardfileSeq(boardFile.getBoardfileSeq())
                .boardfileName(boardFile.getBoardfileName())
                .boardfileSize(boardFile.getBoardfileSize())
                .boardSeq(boardFile.getBoard().getBoardSeq())
                .fileUrl(boardFile.getFileUrl())
                .build();
    }
}
