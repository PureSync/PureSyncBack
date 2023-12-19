package com.fcc.PureSync.controller;

import com.fcc.PureSync.dto.QnaBoardDto;
import com.fcc.PureSync.dto.ResultDto;
import com.fcc.PureSync.jwt.CustomUserDetails;
import com.fcc.PureSync.service.QnaBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/qnaBoard")
@RequiredArgsConstructor
public class QnaBoardController {
    private final QnaBoardService qnaBoardService;

    /**
     * 등록
     */
    @PostMapping
    public ResultDto createQnaBoard(@AuthenticationPrincipal CustomUserDetails customUserDetails, QnaBoardDto qnaBoardDto, List<MultipartFile> file) throws IOException {
        String memId = customUserDetails.getUsername();
        return qnaBoardService.createQnaBoard(memId, qnaBoardDto, file);
    }

    /**
     * 수정
     */
    @PutMapping("/{qnaBoardSeq}")
    public ResultDto updateQnaBoard(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long qnaBoardSeq, QnaBoardDto qnaBoardDto, List<MultipartFile> file) throws IOException {
        String memId = customUserDetails.getUsername();
        return qnaBoardService.updateQnaBoard(memId, qnaBoardSeq, qnaBoardDto, file);
    }

    /**
     * 삭제
     */
    @DeleteMapping("/{qnaBoardSeq}")
    public ResultDto deleteQnaBoard(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long qnaBoardSeq) {
        String memId = customUserDetails.getUsername();
        return qnaBoardService.deleteQnaBoard(memId, qnaBoardSeq);
    }

    /**
     * 조회(단일)
     */
    @GetMapping("/{qnaBoardSeq}")
    public ResultDto detailQnaBoard(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long qnaBoardSeq) {
        String memId = customUserDetails.getUsername();
        return qnaBoardService.detailQnaBoard(memId, qnaBoardSeq);
    }

    /**
     * 조회(전체)
     */
    @GetMapping
    public ResultDto getAllQnaBoards(@AuthenticationPrincipal CustomUserDetails customUserDetails, Pageable pageable) {
        String memId = customUserDetails.getUsername();
        return qnaBoardService.findAllQnaBoard(memId, pageable);

    }

    /**
     * 파일 조회
     */
    @GetMapping("/{qnaBoardSeq}/file")
    public ResultDto getQnaBoardFile(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long qnaBoardSeq, Pageable pageable){
        String memId = customUserDetails.getUsername();
        return qnaBoardService.findFileChk(memId, qnaBoardSeq, pageable);
    }



}