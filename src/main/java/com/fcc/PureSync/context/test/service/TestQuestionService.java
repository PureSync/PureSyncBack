package com.fcc.PureSync.context.test.service;

import com.fcc.PureSync.common.ResultDto;
import com.fcc.PureSync.context.test.dto.TestQuestionDto;
import com.fcc.PureSync.context.test.entity.TestQuestion;
import com.fcc.PureSync.context.test.repository.TestQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestQuestionService {

    private final TestQuestionRepository testQuestionRepository;

    private ResultDto findAllTests(Long testSeq, Pageable pageable, String successMessage) {
        List<TestQuestion> tests  = testQuestionRepository.findByTestSeq(testSeq, pageable);
        List<TestQuestionDto> testDto = tests.stream()
                .map(TestQuestionDto::toDto)
                .toList();

        HashMap<String, Object> map = new HashMap<>();
        map.put("tests", testDto);
        return ResultDto.of(HttpStatus.OK.value(), HttpStatus.OK, successMessage, map);
    }

    public ResultDto findAllStressTests(Pageable pageable) {
        return findAllTests(1L, pageable, "스트레스 테스트 전체 조회 성공");
    }

    public ResultDto findAllDepressionTests(Pageable pageable) {
        return findAllTests(2L, pageable, "우울증 테스트 전체 조회 성공");
    }
}
