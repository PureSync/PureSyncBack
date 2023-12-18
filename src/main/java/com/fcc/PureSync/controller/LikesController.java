package com.fcc.PureSync.controller;

import com.fcc.PureSync.dto.ResultDto;
import com.fcc.PureSync.repository.MemberRepository;
import com.fcc.PureSync.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class LikesController {

    private final MemberRepository memberRepository;
    private final LikesService likeService;
    /**
     * 좋아요 등록 , 취소
     */
    @PostMapping("/{boardSeq}/likes")
    public ResultDto createLike(@PathVariable Long boardSeq, String id) {

        return likeService.createLike(boardSeq, id);
    }

    @GetMapping("{boardSeq}/likes")
    public ResultDto findLike(@PathVariable Long boardSeq, String id ){
        return likeService.findLike(boardSeq,id);
    }

    @GetMapping("{boardSeq}/mylikes")
    public ResultDto findMyLike(@PathVariable Long boardSeq, String id ){
        return likeService.findMyLike(boardSeq,id);
    }


}
