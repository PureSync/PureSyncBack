package com.fcc.PureSync.service;

import com.fcc.PureSync.dto.BoardDto;
import com.fcc.PureSync.dto.LikesDto;
import com.fcc.PureSync.dto.ResultDto;
import com.fcc.PureSync.entity.Board;
import com.fcc.PureSync.entity.Likes;
import com.fcc.PureSync.entity.Member;
import com.fcc.PureSync.exception.CustomException;
import com.fcc.PureSync.exception.CustomExceptionCode;
import com.fcc.PureSync.repository.BoardRepository;
import com.fcc.PureSync.repository.LikesRepository;
import com.fcc.PureSync.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final LikesRepository likesRepository;

    public ResultDto buildResultDto(int code, HttpStatus status, String msg, HashMap<String, Object> map) {
        return ResultDto.builder()
                .code(code)
                .httpStatus(status)
                .message(msg)
                .data(map)
                .build();
    }

    private void boardStatusChk(Board board) {
        if (board.getBoardStatus() == 0) {
            throw new CustomException(CustomExceptionCode.ALREADY_DELETED_ARTICLE);
        }
    }

    public ResultDto createLike(Long boardSeq, String id) {

        Member member = memberRepository.findByMemId(id)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));
        Board board = boardRepository.findById(boardSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_BOARD));
        Likes findLike = likesRepository.findByMemberAndBoard(member, board);
        boardStatusChk(board);

        //좋아요 존재하면 삭제
        if (findLike != null) {
            likesRepository.delete(findLike);
            Long likesCount = boardRepository.countLikesByBoard(board);

            Board updatedBoard = Board.builder()
                    .boardSeq(board.getBoardSeq())
                    .boardName(board.getBoardName())
                    .boardContents(board.getBoardContents())
                    .boardWdate(board.getBoardWdate())
                    .member(member)
                    .boardLikescount(likesCount)
                    .build();

            boardRepository.save(updatedBoard);
            return buildResultDto(HttpStatus.OK.value(), HttpStatus.OK, "좋아요 취소 성공", null);
        } else {
            Likes likes = Likes.builder()
                    .member(member)
                    .board(board)
                    .build();
            likesRepository.save(likes);
            Long likesCount = boardRepository.countLikesByBoard(board);

            Board updatedBoard = Board.builder()
                    .boardSeq(board.getBoardSeq())
                    .boardName(board.getBoardName())
                    .boardContents(board.getBoardContents())
                    .boardWdate(board.getBoardWdate())
                    .member(member)
                    .boardLikescount(likesCount)
                    .build();

            boardRepository.save(updatedBoard);
            LikesDto dto = LikesDto.toDto(likes);
            HashMap<String, Object> map = new HashMap<>();
            map.put("likes", dto);
            return buildResultDto(HttpStatus.CREATED.value(), HttpStatus.CREATED, "좋아요 등록 성공", map);
        }
    }

    public ResultDto findLike(Long boardSeq, String id) {
        Board board = boardRepository.findById(boardSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_ARTICLE));
        Long findLikes = boardRepository.countLikesByBoard(board);

        HashMap<String, Object> map = new HashMap<>();
        map.put("findLikes", findLikes);
        return buildResultDto(HttpStatus.CREATED.value(), HttpStatus.CREATED, "좋아요 개수 확인", map);
    }


    public ResultDto findMyLike(Long boardSeq, String id) {

        Member member = memberRepository.findByMemId(id)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));
        Board board = boardRepository.findById(boardSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_ARTICLE));
        Long findMyLikes = likesRepository.countByMemberAndBoard(member, board);
        HashMap<String, Object> map = new HashMap<>();
        map.put("findMyLikes", findMyLikes);
        return buildResultDto(HttpStatus.CREATED.value(), HttpStatus.CREATED, "좋아요 개수 확인", map);
    }

    public ResultDto boardLikes(Long boardSeq, String memId) {
        //보드 시퀀스로 보드 아이디를 보드 정보를 반환 clear
        Board board = boardRepository.findById(boardSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_ARTICLE));

        //유저로 유정 정보 가져오기
        Member member = memberRepository.findByMemId(memId).orElseThrow(()->new CustomException(CustomExceptionCode.NOT_FOUND_USER));

        //전체 좋아요 수 구하기
        Long totalBoardLikes = boardRepository.countLikesByBoard(board);

        //해당 보드의 좋아요 객체 정보 가져오기
        Likes findLike = likesRepository.findByMemberAndBoard(member, board);

        //멤버객체와 보드 객체로 보드의 좋아요 총 수 구하기 1또는 0
        Long findMyLikes = likesRepository.countByMemberAndBoard(member, board);

        System.out.println("findMyLikes::::::::::" + findMyLikes);
        System.out.println("totalBoardLikes::::::::::" + totalBoardLikes);

        //정보가 있을 때 삭제
        if(findMyLikes==1)
            likesRepository.delete(findLike);

        //정보가 없을 때 등록
        if(findMyLikes==0) {
            Likes updatesLikes = Likes.builder()
                    .member(member)
                    .board(board)
                    .build();
            likesRepository.save(updatesLikes);
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("totalBoardLikes", totalBoardLikes);
        map.put("findMyLikes", findMyLikes);
        return buildResultDto(HttpStatus.CREATED.value(), HttpStatus.CREATED, "좋아요 개수 확인", map);
    }
}
