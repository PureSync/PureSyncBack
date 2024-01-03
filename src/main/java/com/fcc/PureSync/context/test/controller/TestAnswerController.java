package com.fcc.PureSync.context.test.controller;

import com.fcc.PureSync.common.ResultDto;
import com.fcc.PureSync.context.test.dto.TestAnswerDto;
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
        Long memSeq = customUserDetails.getMemSeq();
        return testAnswerService.stressAnswer(memSeq, testAnswerDto);
    }

    @PostMapping("/depression")
    public ResultDto depressionAnswer(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody TestAnswerDto testAnswerDto) {
        Long memSeq = customUserDetails.getMemSeq();
        return testAnswerService.depressionAnswer(memSeq, testAnswerDto);
    }

    @GetMapping("/stress/answer/{testSeq}")
    public ResultDto getAllStressAnswer(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable int testSeq){
        Long memSeq = customUserDetails.getMemSeq();
        return testAnswerService.getAllStressAnswer(memSeq, testSeq);
    }

    @GetMapping("/depression/answer/{testSeq}")
    public ResultDto getAllDepressionAnswer(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable int testSeq){
        Long memSeq = customUserDetails.getMemSeq();
        return testAnswerService.getAllDepressionAnswer(memSeq, testSeq);
    }

    @PutMapping("/stress/{testSeq}")
    public ResultDto updateStressAnswer(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody TestAnswerDto testAnswerDto, @PathVariable int testSeq) {
        Long memSeq = customUserDetails.getMemSeq();
        return testAnswerService.updateStressAnswer(memSeq, testAnswerDto, testSeq);
    }

    @PutMapping("/depression/{testSeq}")
    public ResultDto updateDepressionAnswer(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody TestAnswerDto testAnswerDto, @PathVariable int testSeq) {
        Long memSeq = customUserDetails.getMemSeq();
        return testAnswerService.updateDepressionAnswer(memSeq, testAnswerDto, testSeq);
    }
}
