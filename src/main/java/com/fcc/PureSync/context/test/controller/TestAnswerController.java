package com.fcc.PureSync.context.test.controller;

import com.fcc.PureSync.common.ResultDto;
import com.fcc.PureSync.context.test.dto.TestAnswerDto;
import com.fcc.PureSync.exception.CustomException;
import com.fcc.PureSync.exception.CustomExceptionCode;
import com.fcc.PureSync.jwt.CustomUserDetails;
import com.fcc.PureSync.context.test.service.TestAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestAnswerController {

    private final TestAnswerService testAnswerService;

    @PostMapping("/stress")
    public ResultDto stressAnswer(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody TestAnswerDto testAnswerDto) {
        if (customUserDetails == null)
            throw new CustomException(CustomExceptionCode.INVALID_JWT);

        return testAnswerService.stressAnswer(customUserDetails.getMemSeq(), testAnswerDto);
    }

    @PostMapping("/depression")
    public ResultDto depressionAnswer(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody TestAnswerDto testAnswerDto) {
        if (customUserDetails == null)
            throw new CustomException(CustomExceptionCode.INVALID_JWT);

        return testAnswerService.depressionAnswer(customUserDetails.getMemSeq(), testAnswerDto);
    }

    @GetMapping("/stress/answer/{testSeq}")
    public ResultDto getAllStressAnswer(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable int testSeq){
        if (customUserDetails == null)
            throw new CustomException(CustomExceptionCode.INVALID_JWT);

        return testAnswerService.getAllStressAnswer(customUserDetails.getMemSeq(), testSeq);
    }

    @GetMapping("/depression/answer/{testSeq}")
    public ResultDto getAllDepressionAnswer(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable int testSeq){
        if (customUserDetails == null)
            throw new CustomException(CustomExceptionCode.INVALID_JWT);

        return testAnswerService.getAllDepressionAnswer(customUserDetails.getMemSeq(), testSeq);
    }

    @PutMapping("/stress/{testSeq}")
    public ResultDto updateStressAnswer(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody TestAnswerDto testAnswerDto, @PathVariable int testSeq) {
        if (customUserDetails == null)
            throw new CustomException(CustomExceptionCode.INVALID_JWT);

        return testAnswerService.updateStressAnswer(customUserDetails.getMemSeq(), testAnswerDto, testSeq);
    }

    @PutMapping("/depression/{testSeq}")
    public ResultDto updateDepressionAnswer(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody TestAnswerDto testAnswerDto, @PathVariable int testSeq) {
        if (customUserDetails == null)
            throw new CustomException(CustomExceptionCode.INVALID_JWT);

        return testAnswerService.updateDepressionAnswer(customUserDetails.getMemSeq(), testAnswerDto, testSeq);
    }
}
