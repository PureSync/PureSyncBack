package com.fcc.PureSync.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.fcc.PureSync.context.board.dto.BoardDto;
import com.fcc.PureSync.context.board.entity.Board;
import com.fcc.PureSync.context.board.service.BoardService;
import com.fcc.PureSync.context.board.entity.BoardFile;
import com.fcc.PureSync.entity.Member;
import com.fcc.PureSync.context.board.repository.BoardFileRepository;
import com.fcc.PureSync.context.board.repository.BoardRepository;
import com.fcc.PureSync.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.ArgumentMatchers.any;
import static java.util.Optional.of;
import static org.mockito.Mockito.when;

class BoardServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private BoardService boardService;

    MemberRepository memberRepository = mock(MemberRepository.class);

    BoardRepository boardRepository = mock(BoardRepository.class);

    BoardFileRepository boardFileRepository = mock(BoardFileRepository.class);
    AmazonS3Client amazonS3Client = mock(AmazonS3Client.class);

    @InjectMocks
    private final Member mockMem = Member.builder()
            .memSeq(1L)
            .memId("user")
            .memNick("aaa")
            .build();

    MockMultipartFile file1 = new MockMultipartFile(
            "file",
            "111231qweqewqeqweq.png",
            "multipart/form-data",
            "fileContent".getBytes()
    );

    List<MultipartFile> multipartFiles = Collections.singletonList(file1);
    @InjectMocks
    private final BoardFile boardFile = BoardFile.builder()
            .boardfileSize((long) file1.getSize())
            .fileUrl(file1.getName())
            .boardfileName(file1.getOriginalFilename())
            .build();

    //각 테스트 메서드 전에 실행되는 메서드를 나타내는 어노테이션
    @BeforeEach
    void setUp(){
        boardService = new BoardService(boardRepository,boardFileRepository,memberRepository,amazonS3Client);
    }
    @Test
    @DisplayName("board게시글 작성 성공")
    void createBoard() {
        BoardDto boardDto = BoardDto.builder()
                .boardSeq(1L)
                .boardName("1번글제목")
                .boardContents("1번글내용")
                .boardStatus(1)
                .build();

        Board mockBoard = mock(Board.class);
        Member mockMember = mock(Member.class);

        when(memberRepository.findByMemId(anyString()))
                .thenReturn(of(mockMem));

        when(boardRepository.save(any()))
                .thenReturn(mockBoard);

        when(mockBoard.getBoardSeq())
                .thenReturn(boardDto.getBoardSeq());
        System.out.println("boardDto: " + boardDto.toString());
        Long board_seq = boardDto.getBoardSeq();
        System.out.println("board_seq: " + board_seq);
        //특정 예외가 발생하지 않는지 확인
        Assertions.assertDoesNotThrow(()-> boardService.createBoard(boardDto,null, mockMem.getMemId()));
    }
}