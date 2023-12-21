package com.fcc.PureSync.controller;

import com.fcc.PureSync.dto.MemberInfoDto;
import com.fcc.PureSync.dto.MemberInfoUpdateDto;
import com.fcc.PureSync.dto.MemberPasswordDto;
import com.fcc.PureSync.dto.ResultDto;
import com.fcc.PureSync.jwt.CustomUserDetails;
import com.fcc.PureSync.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/my")
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    // 내 정보
    @GetMapping
    public ResultDto myInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return mypageService.getMyInfo(customUserDetails.getMemSeq());
    }

    // 내가쓴글
    @GetMapping("/posts")
    public ResultDto myPosts(
            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return mypageService.getPosts(pageNo, customUserDetails.getMemSeq());
    }

    // 좋아요누른글
    @GetMapping("/liked-posts")
    public ResultDto myPostsLikes(
            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return mypageService.myPostsLikes(pageNo, customUserDetails.getMemSeq());
    }

    // 내정보 업데이트 (기본정보 - 닉네임, 이미지)
    @PutMapping
    public ResultDto updateMemberInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestPart MemberInfoDto memberInfo,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {
        return mypageService.updateMemberInfo(customUserDetails.getMemSeq(), memberInfo, file);
    }

    // 내 신체정보 업데이트
    @PostMapping
    public ResultDto insertMemberBodyInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody MemberInfoUpdateDto dto) {
        return mypageService.insertMemberBodyInfo(customUserDetails.getMemSeq(), dto);
    }

    // 내정보 업데이트 (기본정보 - 패스워드)
    @PutMapping("/password")
    public ResultDto updateNick(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody MemberPasswordDto dto) {
        return mypageService.updatePassword(customUserDetails.getMemSeq(), dto);
    }

    // 회원 탈퇴
    @DeleteMapping
    public ResultDto deleteMember(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return mypageService.deleteMember(customUserDetails.getMemSeq());
    }


}
