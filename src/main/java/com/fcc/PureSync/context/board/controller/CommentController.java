package com.fcc.PureSync.context.board.controller;

import com.fcc.PureSync.context.board.dto.CommentDto;
import com.fcc.PureSync.core.ResultDto;
import com.fcc.PureSync.core.jwt.CustomUserDetails;
import com.fcc.PureSync.context.member.repository.MemberRepository;
import com.fcc.PureSync.context.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class CommentController {


    private final MemberRepository memberRepository;
    private final CommentService commentService;

    /**
     * 등록
     */
    @PostMapping("/{boardSeq}/comments")
    public ResultDto createComment(@PathVariable Long boardSeq, @RequestBody CommentDto commentDto,  @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String id = customUserDetails.getUsername();
        return commentService.createComment(commentDto, id, boardSeq);
    }

    /**
     * 수정
     */
    @PutMapping("/{boardSeq}/comments/{cmtSeq}")
    public ResultDto updateComment(@PathVariable Long boardSeq, @PathVariable Long cmtSeq,@RequestBody CommentDto commentDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String id = customUserDetails.getUsername();
        return commentService.updateComment(boardSeq, cmtSeq, commentDto, id);
    }

    /**
     * 삭제
     */
    @DeleteMapping("/{boardSeq}/comments/{cmtSeq}")
    public ResultDto deleteComment(@PathVariable Long boardSeq,@PathVariable Long cmtSeq,  @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String id = customUserDetails.getUsername();
        return commentService.deleteComment(boardSeq,cmtSeq, id);
    }
    /**
     * 조회
     */
    @GetMapping("/{boardSeq}/comments")
    public ResultDto getComment(Pageable pageable, @PathVariable Long boardSeq){
        return commentService.getComment(pageable,boardSeq);
    }
}
