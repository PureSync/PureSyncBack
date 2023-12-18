package com.fcc.PureSync.controller;

import com.fcc.PureSync.dto.ResultDto;
import com.fcc.PureSync.dto.TestAnswerDto;
import com.fcc.PureSync.service.TestAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestAnswerController {

    private final TestAnswerService testAnswerService;

    @PostMapping("/stress")
    public ResultDto stressAnswer(@RequestBody TestAnswerDto testAnswerDto, String id) {
        return testAnswerService.stressAnswer(testAnswerDto, id);
    }

    @PostMapping("/depression")
    public ResultDto depressionAnswer(@RequestBody TestAnswerDto testAnswerDto, String id) {
        return testAnswerService.depressionAnswer(testAnswerDto, id);
    }

    @GetMapping("/stress/answer/{memSeq}/{testSeq}")
    public ResultDto getAllStressAnswer(@PathVariable Long memSeq, @PathVariable int testSeq){
        return testAnswerService.getAllStressAnswer(memSeq, testSeq);
    }

    @GetMapping("/depression/answer/{memSeq}/{testSeq}")
    public ResultDto getAllDepressionAnswer(@PathVariable Long memSeq, @PathVariable int testSeq){
        return testAnswerService.getAllDepressionAnswer(memSeq, testSeq);
    }

    @PutMapping("/stress/{memSeq}/{testSeq}")
    public ResultDto updateStressAnswer(@RequestBody TestAnswerDto testAnswerDto, @PathVariable Long memSeq, @PathVariable int testSeq) {
        return testAnswerService.updateStressAnswer(testAnswerDto, memSeq, testSeq);
    }

    @PutMapping("/depression/{memSeq}/{testSeq}")
    public ResultDto updateDepressionAnswer(@RequestBody TestAnswerDto testAnswerDto, @PathVariable Long memSeq, @PathVariable int testSeq) {
        return testAnswerService.updateDepressionAnswer(testAnswerDto, memSeq, testSeq);
    }
}
