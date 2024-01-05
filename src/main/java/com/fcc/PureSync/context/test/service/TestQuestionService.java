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

    public ResultDto findAllStressTests(Pageable pageable) {
        List<TestQuestion> stressTests  = testQuestionRepository.findByTestSeq(1L, pageable);
        List<TestQuestionDto> stressTestDto = stressTests.stream()
                .map(TestQuestionDto::toDto)
                .toList();

        HashMap<String, Object> map = new HashMap<>();
        map.put("stressTests", stressTestDto);
        return ResultDto.of(HttpStatus.OK.value(), HttpStatus.OK, "스트레스 테스트 전체 조회 성공", map);

    }

    public ResultDto findAllDepressionTests(Pageable pageable) {
        List<TestQuestion> depressionTests  = testQuestionRepository.findByTestSeq(2L, pageable);
        List<TestQuestionDto> depressionTestDto = depressionTests.stream()
                .map(TestQuestionDto::toDto)
                .toList();

        HashMap<String, Object> map = new HashMap<>();
        map.put("depressionTests", depressionTestDto);
        return ResultDto.of(HttpStatus.OK.value(), HttpStatus.OK, "우울증 테스트 전체 조회 성공", map);
    }
}
