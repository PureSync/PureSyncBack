package com.fcc.PureSync.context.qnaBoard.controller;

import com.fcc.PureSync.context.qnaBoard.dto.QnaBoardDto;
import com.fcc.PureSync.core.ResultDto;
import com.fcc.PureSync.core.jwt.CustomUserDetails;
import com.fcc.PureSync.core.exception.CustomException;
import com.fcc.PureSync.core.exception.CustomExceptionCode;
import com.fcc.PureSync.core.jwt.CustomUserDetails;

import com.fcc.PureSync.context.qnaBoard.service.QnaBoardService;
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
        if (customUserDetails == null)
            throw new CustomException(CustomExceptionCode.INVALID_JWT);

        return qnaBoardService.createQnaBoard(customUserDetails.getUsername(), qnaBoardDto, file);
    }

    /**
     * 수정
     */
    @PutMapping("/{qnaBoardSeq}")
    public ResultDto updateQnaBoard(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long qnaBoardSeq, QnaBoardDto qnaBoardDto, List<MultipartFile> file) throws IOException {
        if (customUserDetails == null)
            throw new CustomException(CustomExceptionCode.INVALID_JWT);

        return qnaBoardService.updateQnaBoard(customUserDetails.getUsername(), qnaBoardSeq, qnaBoardDto, file);
    }

    /**
     * 삭제
     */
    @DeleteMapping("/{qnaBoardSeq}")
    public ResultDto deleteQnaBoard(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long qnaBoardSeq) {
        if (customUserDetails == null)
            throw new CustomException(CustomExceptionCode.INVALID_JWT);

        return qnaBoardService.deleteQnaBoard(customUserDetails.getUsername(), qnaBoardSeq);
    }

    /**
     * 조회(단일)
     */
    @GetMapping("/{qnaBoardSeq}")
    public ResultDto detailQnaBoard(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long qnaBoardSeq) {
        if (customUserDetails == null)
            throw new CustomException(CustomExceptionCode.INVALID_JWT);

        return qnaBoardService.detailQnaBoard(customUserDetails.getUsername(), qnaBoardSeq);
    }

    /**
     * 조회(전체)
     */
    @GetMapping
    public ResultDto getAllQnaBoards(@AuthenticationPrincipal CustomUserDetails customUserDetails, Pageable pageable) {
        if (customUserDetails == null)
            throw new CustomException(CustomExceptionCode.INVALID_JWT);

        return qnaBoardService.findAllQnaBoard(customUserDetails.getUsername(), pageable);

    }

    /**
     * 파일 조회
     */
    @GetMapping("/{qnaBoardSeq}/file")
    public ResultDto getQnaBoardFile(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long qnaBoardSeq, Pageable pageable){
        if (customUserDetails == null)
            throw new CustomException(CustomExceptionCode.INVALID_JWT);

        return qnaBoardService.findFileChk(customUserDetails.getUsername(), qnaBoardSeq, pageable);
    }
}