package com.fcc.PureSync.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fcc.PureSync.entity.Board;
import com.fcc.PureSync.entity.BoardFile;
import com.fcc.PureSync.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    private Long boardSeq;
    private String boardName;
    private String boardContents;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime boardWdate;
    private Long boardLikescount;
    private Long memSeq;
    private String memId;
    private Integer boardStatus;
    private List<BoardFileDto> boardFile;
    private List<CommentDto> comment;




    //@Builder.Default 시도해보기
    public static BoardDto toDto(Board board) {
        return BoardDto.builder()
                .boardSeq(board.getBoardSeq())
                .boardName(board.getBoardName())
                .boardContents(board.getBoardContents())
                .boardWdate(board.getBoardWdate())
                .boardLikescount(board.getBoardLikescount())
                .memSeq(board.getMember().getMemSeq())
                .boardStatus(board.getBoardStatus())
                .build();
    }

    public static BoardDto BoardDetailDto(Board board) {
        List<CommentDto> commentDtoList = board.getComments().stream()
                .filter(Comment-> Comment.getCmtStatus()!= 0)
                .map(CommentDto::toDto)
                .sorted(Comparator.comparing(CommentDto::getCmtWdate).reversed())
                .toList();
        List<BoardFileDto> boardFileDtoList = board.getBoardFile().stream()
                .map(BoardFileDto::toDto)
                .toList();
        return BoardDto.builder()
                .boardSeq(board.getBoardSeq())
                .boardName(board.getBoardName())
                .boardContents(board.getBoardContents())
                .boardWdate(board.getBoardWdate())
                .boardLikescount(board.getBoardLikescount())
                .memSeq(board.getMember().getMemSeq())
                .memId(board.getMember().getMemId())
                .boardStatus(board.getBoardStatus())
                .boardFile(boardFileDtoList)
                .comment(commentDtoList)
                .build();
    }

    public static BoardDto BoardAllDetailDto(Board board) {

        return BoardDto.builder()
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