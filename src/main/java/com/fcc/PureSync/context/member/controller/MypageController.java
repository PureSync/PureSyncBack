package com.fcc.PureSync.context.member.controller;

import com.fcc.PureSync.context.member.dto.MemberInfoDto;
import com.fcc.PureSync.context.member.dto.MemberInfoUpdateDto;
import com.fcc.PureSync.context.member.dto.MemberPasswordDto;
import com.fcc.PureSync.core.ResultDto;
import com.fcc.PureSync.core.exception.CustomException;
import com.fcc.PureSync.core.exception.CustomExceptionCode;
import com.fcc.PureSync.core.jwt.CustomUserDetails;
import com.fcc.PureSync.context.member.service.MypageService;
import lombok.RequiredArgsConstructor;
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
        if (customUserDetails == null) {
            throw new CustomException(CustomExceptionCode.INVALID_JWT);
        }

        return mypageService.getMyInfo(customUserDetails.getMemSeq());
    }

    // 내가쓴글
    @GetMapping("/posts")
    public ResultDto myPosts(
            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails == null) {
            throw new CustomException(CustomExceptionCode.INVALID_JWT);
        }

        return mypageService.getPosts(pageNo, customUserDetails.getMemSeq());
    }

    // 좋아요누른글
    @GetMapping("/liked-posts")
    public ResultDto myPostsLikes(
            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails == null) {
            throw new CustomException(CustomExceptionCode.INVALID_JWT);
        }
        return mypageService.myPostsLikes(pageNo, customUserDetails.getMemSeq());
    }

    // 내정보 업데이트 (기본정보 - 닉네임, 이미지)
    @PutMapping
    public ResultDto updateMemberInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestPart MemberInfoDto memberInfo,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {
        if (customUserDetails == null) {
            throw new CustomException(CustomExceptionCode.INVALID_JWT);
        }
        return mypageService.updateMemberInfo(customUserDetails.getMemSeq(), memberInfo, file);
    }

    // 내 신체정보 업데이트
    @PostMapping
    public ResultDto insertMemberBodyInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody MemberInfoUpdateDto dto) {
        if (customUserDetails == null) {
            throw new CustomException(CustomExceptionCode.INVALID_JWT);
        }
        return mypageService.insertMemberBodyInfo(customUserDetails.getMemSeq(), dto);
    }

    // 내정보 업데이트 (기본정보 - 패스워드)
    @PutMapping("/password")
    public ResultDto updateNick(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody MemberPasswordDto dto) {
        if (customUserDetails == null) {
            throw new CustomException(CustomExceptionCode.INVALID_JWT);
        }
        return mypageService.updatePassword(customUserDetails.getMemSeq(), dto);
    }

    // 회원 탈퇴
    @DeleteMapping
    public ResultDto deleteMember(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails == null) {
            throw new CustomException(CustomExceptionCode.INVALID_JWT);
        }
        return mypageService.deleteMember(customUserDetails.getMemSeq());
    }


}
