package com.fcc.PureSync.context.qnaBoard.controller;

import com.fcc.PureSync.context.qnaBoard.dto.QnaCommentDto;
import com.fcc.PureSync.core.ResultDto;
import com.fcc.PureSync.context.member.repository.MemberRepository;
import com.fcc.PureSync.context.qnaBoard.service.QnaCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qnaBoard")
@RequiredArgsConstructor
public class QnaCommentController {


    private final MemberRepository memberRepository;
    private final QnaCommentService commentService;

    /**
     * 등록
     */
    @PostMapping("/{qnaBoardSeq}/comments")
    public ResultDto createComment(@PathVariable Long qnaBoardSeq, @RequestBody QnaCommentDto qnaCommentDto, String id) {
        return commentService.createQnaComment(qnaBoardSeq, qnaCommentDto, id);
    }

    /**
     * 수정
     */
    @PutMapping("/{qnaBoardSeq}/comments/{qnaCmtSeq}")
    public ResultDto updateComment(@PathVariable Long qnaBoardSeq, @PathVariable Long qnaCmtSeq, @RequestBody QnaCommentDto qnaCommentDto, String id) {
        return commentService.updateQnaComment(qnaBoardSeq, qnaCmtSeq, qnaCommentDto, id);
    }

    /**
     * 삭제
     */
    @DeleteMapping("/{qnaBoardSeq}/comments/{qnaCmtSeq}")
    public ResultDto deleteComment(@PathVariable Long qnaBoardSeq, @PathVariable Long qnaCmtSeq, String id) {
        return commentService.deleteQnaComment(qnaBoardSeq, qnaCmtSeq, id);
    }
    /**
     * 조회
     */
    @GetMapping("/{qnaBoardSeq}/comments")
    public ResultDto getComment(Pageable pageable, @PathVariable Long qnaBoardSeq){
        return commentService.getQnaComment(pageable, qnaBoardSeq);
    }
}
