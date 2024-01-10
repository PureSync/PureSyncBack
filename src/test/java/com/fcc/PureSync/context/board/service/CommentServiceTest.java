package com.fcc.PureSync.context.board.service;

import com.fcc.PureSync.context.board.dto.CommentDto;
import com.fcc.PureSync.context.board.entity.Board;
import com.fcc.PureSync.context.board.entity.Comment;
import com.fcc.PureSync.context.board.repository.BoardRepository;
import com.fcc.PureSync.context.board.repository.CommentRepository;
import com.fcc.PureSync.core.ResultDto;
import com.fcc.PureSync.context.member.entity.Member;
import com.fcc.PureSync.core.exception.CustomException;
import com.fcc.PureSync.core.exception.CustomExceptionCode;
import com.fcc.PureSync.context.member.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;


import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    MemberRepository memberRepository = mock(MemberRepository.class);

    BoardRepository boardRepository = mock(BoardRepository.class);

    CommentRepository commentRepository = mock(CommentRepository.class);

    @InjectMocks
    private final Member mockMem = Member.builder()
            .memId("user")
            .memNick("aaa")
            .build();

    private final Board mockBoard = Board.builder()
            .boardName("게시글작성")
            .boardContents("게시글내용")
            .member(mockMem)
            .build();
    private final Comment mockComment = Comment.builder()
            .cmtContents("댓글")
            .board(mockBoard)
            .member(mockMem)
            .build();
    private final Comment mockComment2 = Comment.builder()
            .cmtContents("댓글2")
            .board(mockBoard)
            .member(mockMem)
            .build();

    @BeforeEach
    void setUp(){
        commentService = new CommentService(boardRepository,memberRepository,commentRepository);
    }
    @Test
    @DisplayName("게시글이 없을때")
    void boardStatusChk_Exception(){
        Board board = Board.builder()
                .boardName("삭제된 게시글")
                .boardContents("삭제된 내용")
                .member(mockMem)
                .boardStatus(0)
                .build();
        CustomException customException = assertThrows(CustomException.class,()-> commentService.boardStatusChk(board));
        assertEquals(CustomExceptionCode.ALREADY_DELETED_ARTICLE,customException.getExceptionCode());
    }

    @Test
    @DisplayName("댓글이 없을때")
    void commentStatusChk_Exception(){
        Comment comment = Comment.builder()
                .cmtContents("댓글")
                .board(mockBoard)
                .member(mockMem)
                .cmtStatus(0)
                .build();
        CustomException customException = assertThrows(CustomException.class,()-> commentService.commentStatusChk(comment));
        assertEquals(CustomExceptionCode.ALREADY_DELETED_COMMENT,customException.getExceptionCode());
    }

    @Test
    @DisplayName("유저가 댓글 작성 성공")
        void createComment_success() {
        CommentDto commentDto = CommentDto.builder()
                .cmtContents("댓글등록")
                .boardSeq(mockBoard.getBoardSeq())
                .build();
        Comment mockCmt = mock(Comment.class);

        when(memberRepository.findByMemId(mockMem.getMemId()))
                .thenReturn(of(mockMem));

        when(boardRepository.findById(mockBoard.getBoardSeq()))
                .thenReturn(of(mockBoard));

        when(commentRepository.save(any(Comment.class)))
                .thenReturn(mockCmt);

        Assertions.assertDoesNotThrow(()-> commentService.createComment(commentDto,mockMem.getMemId(),mockBoard.getBoardSeq()));
    }

    @Test
    @DisplayName("유저가 댓글 수정 성공")
    void updateComment_success() {
        CommentDto commentDto = CommentDto.builder()
                .cmtContents("댓글수정")
                .boardSeq(mockBoard.getBoardSeq())
                .build();
        when(commentRepository.findById(mockComment.getCmtSeq()))
                .thenReturn(of(mockComment));
        when(memberRepository.findByMemId(mockMem.getMemId()))
                .thenReturn(of(mockMem));
        when((boardRepository.findById(mockBoard.getBoardSeq())))
                .thenReturn(of(mockBoard));
        Assertions.assertDoesNotThrow(()->commentService.updateComment(mockBoard.getBoardSeq(),mockComment.getCmtSeq(),commentDto,mockMem.getMemId()));

    }

    @Test
    @DisplayName("유저가 댓글 삭제 성공")
    void deleteComment_success() {
        when(commentRepository.findById(mockComment.getCmtSeq()))
                .thenReturn(of(mockComment));
        when(memberRepository.findByMemId(mockMem.getMemId()))
                .thenReturn(of(mockMem));
        when((boardRepository.findById(mockBoard.getBoardSeq())))
                .thenReturn(of(mockBoard));

        Assertions.assertDoesNotThrow(()->commentService.deleteComment(mockBoard.getBoardSeq(),mockComment.getCmtSeq(),mockMem.getMemId()));
    }

    @Test
    @DisplayName("게시글에 존재하는 댓글 조회 성공")
    void getComment_success() {
        when((boardRepository.findById(mockBoard.getBoardSeq())))
                .thenReturn(of(mockBoard));
        Pageable pageable = mock(Pageable.class);
        List<Comment> commentList = Arrays.asList(mockComment,mockComment2);
        when(commentRepository.findByCmtStatusNotAndBoard_BoardSeqOrderByCmtWdateDesc(pageable, 0, mockBoard.getBoardSeq()))
                .thenReturn(commentList);
        List<CommentDto> commentDtoList = commentList.stream()
                .map(CommentDto::toDto)
                .toList();
        ResultDto resultDto = commentService.getComment(pageable, mockBoard.getBoardSeq());
        assertThat(commentDtoList).hasSize(2);
        assertThat(commentDtoList.get(0).getCmtContents()).isEqualTo("댓글");
        assertThat(commentDtoList.get(1).getCmtContents()).isEqualTo("댓글2");
    }


}