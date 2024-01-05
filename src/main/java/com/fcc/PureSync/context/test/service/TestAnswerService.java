package com.fcc.PureSync.context.test.service;

import com.fcc.PureSync.common.ResultDto;
import com.fcc.PureSync.context.test.dto.TestAnswerDto;
import com.fcc.PureSync.entity.Member;
import com.fcc.PureSync.context.test.entity.TestAnswer;
import com.fcc.PureSync.exception.CustomException;
import com.fcc.PureSync.exception.CustomExceptionCode;
import com.fcc.PureSync.repository.MemberRepository;
import com.fcc.PureSync.context.test.repository.TestAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.fcc.PureSync.context.test.dto.TestAnswerDto.toDto;


@Service
@RequiredArgsConstructor
public class TestAnswerService {
    private final MemberRepository memberRepository;
    private final TestAnswerRepository testAnswerRepository;

    public ResultDto buildResultDto(int code, HttpStatus status, String msg, HashMap<String, Object> map) {
        return ResultDto.builder()
                .code(code)
                .httpStatus(status)
                .message(msg)
                .data(map)
                .build();
    }

    private ResultDto answerTest(Long memSeq, int testSeq, String testType, TestAnswerDto testAnswerDto) {
        Member member = memberRepository.findByMemSeq(memSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));

        if (testSeq != 1 && testSeq != 2) {
            throw new CustomException(CustomExceptionCode.NOT_FOUND_TEST);
        }

        TestAnswer testAnswer = TestAnswer.builder()
                .testSeq(testSeq)
                .testAns(testAnswerDto.getTestAns())
                .total(calculateTotalScore(testAnswerDto.getTestAns()))
                .member(member)
                .build();

        testAnswerRepository.save(testAnswer);
        HashMap<String, Object> map = new HashMap<>();
        map.put(testType, toDto(testAnswer));

        String testTypeName = (testSeq == 1) ? "스트레스" : "우울증";
        return buildResultDto(HttpStatus.OK.value(), HttpStatus.OK, testTypeName + " 테스트 답변 완료", map);
    }

    public ResultDto stressAnswer(Long memSeq, TestAnswerDto testAnswerDto) {
        return answerTest(memSeq, 1, "stressTestAnswer", testAnswerDto);
    }

    public ResultDto depressionAnswer(Long memSeq, TestAnswerDto testAnswerDto) {
        return answerTest(memSeq, 2, "depressionTestAnswer", testAnswerDto);
    }

    private ResultDto getAllAnswer(Long memSeq, int testSeq, String testType) {
        Member member = memberRepository.findByMemSeq(memSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));

        TestAnswer testAnswer = testAnswerRepository.findByMemberAndTestSeq(member, testSeq);

        HashMap<String, Object> map = new HashMap<>();
        map.put(testType, testAnswer);

        String testTypeName = (testSeq == 1) ? "스트레스" : "우울증";
        return buildResultDto(HttpStatus.OK.value(), HttpStatus.OK, testTypeName + " 테스트 답변 조회 성공", map);
    }

    public ResultDto getAllStressAnswer(Long memSeq, int testSeq) {
        return getAllAnswer(memSeq, testSeq, "allStressAnswer");
    }

    public ResultDto getAllDepressionAnswer(Long memSeq, int testSeq) {
        return getAllAnswer(memSeq, testSeq, "allDepressionAnswer");
    }

    private ResultDto updateAnswer(Long memSeq, int testSeq, String testType, TestAnswerDto testAnswerDto) {
        Member member = memberRepository.findByMemSeq(memSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));

        // 기존 데이터를 찾기
        TestAnswer testAnswer = testAnswerRepository.findByMemberAndTestSeq(member, testSeq);

        // 기존 데이터 업데이트
        TestAnswer updatedTestAnswer = TestAnswer.builder()
                .ansSeq(testAnswer.getAnsSeq())
                .testSeq(testAnswer.getTestSeq())
                .testAns(testAnswerDto.getTestAns())
                .total(calculateTotalScore(testAnswerDto.getTestAns()))
                .member(member)
                .build();

        testAnswerRepository.save(updatedTestAnswer);

        HashMap<String, Object> map = new HashMap<>();
        map.put(testType, toDto(updatedTestAnswer));

        String testTypeName = (testSeq == 1) ? "스트레스" : "우울증";
        return buildResultDto(HttpStatus.OK.value(), HttpStatus.OK, testTypeName + " 테스트 답변 수정 완료", map);
    }

    public ResultDto updateStressAnswer(Long memSeq, TestAnswerDto testAnswerDto, int testSeq) {
        return updateAnswer(memSeq, testSeq, "stressTestAnswer", testAnswerDto);
    }

    public ResultDto updateDepressionAnswer(Long memSeq, TestAnswerDto testAnswerDto, int testSeq) {
        return updateAnswer(memSeq, testSeq, "depressionTestAnswer", testAnswerDto);
    }

    private int calculateTotalScore(String testAns) {
        List<Integer> testAnsValues = Arrays.stream(testAns.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        // 총합 계산
        return testAnsValues.stream().mapToInt(Integer::intValue).sum();
    }
}