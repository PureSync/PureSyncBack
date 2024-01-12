package com.fcc.PureSync.context.qnaBoard.service;

import com.fcc.PureSync.context.qnaBoard.dto.QnaCommentDto;
import com.fcc.PureSync.core.ResultDto;
import com.fcc.PureSync.context.qnaBoard.entity.QnaBoard;
import com.fcc.PureSync.context.qnaBoard.entity.QnaComment;
import com.fcc.PureSync.context.member.entity.Member;
import com.fcc.PureSync.core.exception.CustomException;
import com.fcc.PureSync.core.exception.CustomExceptionCode;
import com.fcc.PureSync.context.qnaBoard.repository.QnaBoardRepository;
import com.fcc.PureSync.context.qnaBoard.repository.QnaCommentRepository;
import com.fcc.PureSync.context.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static com.fcc.PureSync.context.qnaBoard.dto.QnaCommentDto.toDto;

@Service
@RequiredArgsConstructor
public class QnaCommentService {

    private final QnaBoardRepository qnaBoardRepository;
    private final MemberRepository memberRepository;
    private final QnaCommentRepository qnaCommentRepository;

    private void qnaBoardStatusChk(QnaBoard qnaBoard) {
        if (qnaBoard.getQnaBoardStatus() == 0) {
            throw new CustomException(CustomExceptionCode.ALREADY_DELETED_ARTICLE);
        }
    }

    public ResultDto createQnaComment(Long qnaBoardSeq, QnaCommentDto qnaCommentDto, String id) {
        id = "aaa";
        Member member = memberRepository.findByMemId(id)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));
        QnaBoard qnaBoard = qnaBoardRepository.findById(qnaBoardSeq)
                .orElseThrow(()-> new CustomException(CustomExceptionCode.NOT_FOUND_BOARD));
        qnaBoardStatusChk(qnaBoard);

        QnaComment qnaComment = QnaComment.builder()
                .qnaCmtContents(qnaCommentDto.getQnaCmtContents())
                .qnaCmtWriter(qnaCommentDto.getQnaCmtWriter())
                .qnaBoard(qnaBoard)
                .build();

        qnaCommentRepository.save(qnaComment);
        QnaCommentDto dto = toDto(qnaComment);
        HashMap<String, Object> map = new HashMap<>();
        map.put("qnaComment", dto);
        return ResultDto.of(HttpStatus.CREATED.value(), HttpStatus.CREATED, "댓글 생성 성공", map);
    }

    public ResultDto updateQnaComment(Long qnaBoardSeq, Long qnaCmtSeq, QnaCommentDto qnaCommentDto, String id) {
        id = "aaa";///////////////////////////
        Member member = memberRepository.findByMemId(id)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));
        QnaBoard qnaBoard = qnaBoardRepository.findById(qnaBoardSeq)
                .orElseThrow(()-> new CustomException(CustomExceptionCode.NOT_FOUND_BOARD));
        QnaComment qnaComment = qnaCommentRepository.findById(qnaCmtSeq)
                .orElseThrow(()->new CustomException(CustomExceptionCode.NOT_FOUND_COMMENT));
        qnaBoardStatusChk(qnaBoard);

        QnaComment updateQnaComment = QnaComment.builder()
                .qnaCmtSeq(qnaComment.getQnaCmtSeq())
                .qnaCmtContents(qnaCommentDto.getQnaCmtContents())
                .qnaCmtWdate(qnaComment.getQnaCmtWdate())
                .qnaCmtWriter(qnaCommentDto.getQnaCmtWriter())
                .qnaBoard(qnaBoard)
                .build();

        qnaCommentRepository.save(updateQnaComment);
        QnaCommentDto dto = toDto(qnaComment);
        HashMap<String, Object> map = new HashMap<>();
        map.put("qnaComment", dto);
        return ResultDto.of(HttpStatus.CREATED.value(), HttpStatus.CREATED, "댓글 수정 성공", map);

    }

    public ResultDto deleteQnaComment(Long qnaBoardSeq, Long qnaCmtSeq, String id) {
        id = "aaa";////////////////////////
        Member member = memberRepository.findByMemId(id)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));
        QnaBoard qnaBoard = qnaBoardRepository.findById(qnaBoardSeq)
                .orElseThrow(()-> new CustomException(CustomExceptionCode.NOT_FOUND_BOARD));
        QnaComment qnaComment = qnaCommentRepository.findById(qnaCmtSeq)
                .orElseThrow(()->new CustomException(CustomExceptionCode.NOT_FOUND_COMMENT));
        qnaBoardStatusChk(qnaBoard);

        QnaComment updatedComment = QnaComment.builder()
                .qnaCmtSeq(qnaComment.getQnaCmtSeq())
                .qnaCmtContents(qnaComment.getQnaCmtContents())
                .qnaCmtWdate(qnaComment.getQnaCmtWdate())
                .qnaCmtWriter(qnaComment.getQnaCmtWriter())
                .qnaBoard(qnaBoard)
                .build();

        qnaCommentRepository.save(updatedComment);
        QnaCommentDto dto = toDto(qnaComment);
        HashMap<String, Object> map = new HashMap<>();
        map.put("qnaComment", dto);
        return ResultDto.of(HttpStatus.CREATED.value(), HttpStatus.CREATED, "댓글 삭제 성공", map);
    }


    public ResultDto getQnaComment(Pageable pageable, Long qnaBoardSeq) {
        List<QnaComment> qnaCommentList = qnaCommentRepository.findByOrderByQnaCmtWdateDesc(pageable);
        List<QnaCommentDto> qnaCommentDtoList = qnaCommentList.stream()
                .map(QnaCommentDto::toDto)
                .toList();
        HashMap<String, Object> map = new HashMap<>();
        map.put("qnaCommentList", qnaCommentDtoList);
        return ResultDto.of(HttpStatus.OK.value(), HttpStatus.OK, "댓글 전체 조회 성공", map);
    }
}