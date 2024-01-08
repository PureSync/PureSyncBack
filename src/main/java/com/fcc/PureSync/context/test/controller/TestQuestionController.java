package com.fcc.PureSync.context.test.controller;

import com.fcc.PureSync.core.ResultDto;
import com.fcc.PureSync.context.test.service.TestQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestQuestionController {

    private final TestQuestionService testQuestionService;

    /**
     * 조회(전체)
     */
    @GetMapping("/stress")
    public ResultDto getAllStressTests(Pageable pageable) {
        return testQuestionService.findAllStressTests(pageable);

    }

    @GetMapping("/depression")
    public ResultDto getAllDepressionTests(Pageable pageable) {
        return testQuestionService.findAllDepressionTests(pageable);

    }
}
