package com.fcc.PureSync.controller;

import com.fcc.PureSync.dto.MemberInfoDto;
import com.fcc.PureSync.dto.MemberInfoUpdateDto;
import com.fcc.PureSync.dto.MemberPasswordDto;
import com.fcc.PureSync.dto.ResultDto;
import com.fcc.PureSync.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/my")
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    // 내 정보
    @GetMapping("/{memSeq}")
    public ResultDto myInfo(@PathVariable("memSeq") Long memSeq) {
        memSeq = 1L;
        return mypageService.getMyInfo(memSeq);
    }


    // 내가쓴글
    @GetMapping("/posts/{memSeq}")
    public ResultDto myPosts(@PathVariable("memSeq") Long memSeq) {
        memSeq = 1L;
        return mypageService.getPosts(memSeq);
    }

    // 좋아요누른글
    @GetMapping("/liked-posts/{memSeq}")
    public ResultDto myPostsLikes(@PathVariable("memSeq") Long memSeq) {
        memSeq = 1L;
        return mypageService.myPostsLikes(memSeq);
    }

    // 내정보 업데이트 (기본정보 - 닉네임, 이미지)
    @PutMapping("/{memSeq}")
    public ResultDto updateMemberInfo(@PathVariable("memSeq") Long memSeq, MemberInfoDto dto, MultipartFile file) throws IOException {
        return mypageService.updateMemberInfo(memSeq, dto, file);
    }

    // 내 신체정보 업데이트
    @PostMapping("/{memSeq}")
    public ResultDto insertMemberBodyInfo(@PathVariable("memSeq") Long memSeq,  MemberInfoUpdateDto dto) {
        return mypageService.insertMemberBodyInfo(memSeq, dto);
    }

    // 내정보 업데이트 (기본정보 - 패스워드)
    @PutMapping("/password/{memSeq}")
    public ResultDto updateNick(@PathVariable("memSeq") Long memSeq, @RequestBody MemberPasswordDto dto) {
        return mypageService.updatePassword(memSeq, dto);
    }

    @DeleteMapping("/{memSeq}")
    public ResultDto deleteMember (@PathVariable("memSeq") Long memSeq){
        return mypageService.deleteMember(memSeq);
    }


}
