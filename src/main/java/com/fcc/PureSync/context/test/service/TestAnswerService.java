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

    public ResultDto stressAnswer(Long memSeq, TestAnswerDto testAnswerDto) {
        int testSeq = 1;

        Member member = memberRepository.findByMemSeq(memSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));

        if (testSeq != 1) {
            throw new CustomException(CustomExceptionCode.NOT_FOUND_TEST);
        } else {
            TestAnswer testAnswer = TestAnswer.builder()
                    .testSeq(testSeq)
                    .testAns(testAnswerDto.getTestAns())
                    .total(calculateTotalScore(testAnswerDto.getTestAns()))
                    .member(member)
                    .build();

            testAnswerRepository.save(testAnswer);
            HashMap<String, Object> map = new HashMap<>();
            map.put("stressTestAnswer", toDto(testAnswer));

            return buildResultDto(HttpStatus.OK.value(), HttpStatus.OK, "스트레스 테스트 답변 완료", map);
        }
    }

    public ResultDto depressionAnswer(Long memSeq, TestAnswerDto testAnswerDto) {
        int testSeq = 2;
        Member member = memberRepository.findByMemSeq(memSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));

        if (testSeq != 2) {
            throw new CustomException(CustomExceptionCode.NOT_FOUND_TEST);
        } else {
            TestAnswer testAnswer = TestAnswer.builder()
                    .testSeq(testSeq)
                    .testAns(testAnswerDto.getTestAns())
                    .total(calculateTotalScore(testAnswerDto.getTestAns()))
                    .member(member)
                    .build();

            testAnswerRepository.save(testAnswer);
            HashMap<String, Object> map = new HashMap<>();
            map.put("depressionTestAnswer", toDto(testAnswer));

            return buildResultDto(HttpStatus.OK.value(), HttpStatus.OK, "우울증 테스트 답변 완료", map);
        }
    }

    public ResultDto getAllStressAnswer(Long memSeq, int testSeq) {
        Member member = memberRepository.findByMemSeq(memSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));

        TestAnswer testAnswer = testAnswerRepository.findByMemberAndTestSeq(member, testSeq);

        HashMap<String, Object> map = new HashMap<>();
        map.put("allStressAnswer", testAnswer);
        return buildResultDto(HttpStatus.OK.value(), HttpStatus.OK, "스트레스 테스트 답변 조회 성공", map);
    }

    public ResultDto getAllDepressionAnswer(Long memSeq, int testSeq) {
        Member member = memberRepository.findByMemSeq(memSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));

        TestAnswer testAnswer = testAnswerRepository.findByMemberAndTestSeq(member, testSeq);

        HashMap<String, Object> map = new HashMap<>();
        map.put("allDepressionAnswer", testAnswer);
        return buildResultDto(HttpStatus.OK.value(), HttpStatus.OK, "우울증 테스트 답변 조회 성공", map);
    }

    public ResultDto updateStressAnswer(Long memSeq, TestAnswerDto testAnswerDto, int testSeq) {
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
        map.put("stressTestAnswer", toDto(updatedTestAnswer));

        return buildResultDto(HttpStatus.OK.value(), HttpStatus.OK, "스트레스 테스트 답변 수정 완료", map);
    }

    public ResultDto updateDepressionAnswer(Long memSeq, TestAnswerDto testAnswerDto, int testSeq) {
        Member member = memberRepository.findByMemSeq(memSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));

        TestAnswer testAnswer = testAnswerRepository.findByMemberAndTestSeq(member, testSeq);

        TestAnswer updatedTestAnswer = TestAnswer.builder()
                .ansSeq(testAnswer.getAnsSeq())
                .testSeq(testAnswer.getTestSeq())
                .testAns(testAnswerDto.getTestAns())
                .total(calculateTotalScore(testAnswerDto.getTestAns()))
                .member(member)
                .build();

        testAnswerRepository.save(updatedTestAnswer);

        HashMap<String, Object> map = new HashMap<>();
        map.put("depressionTestAnswer", toDto(updatedTestAnswer));

        return buildResultDto(HttpStatus.OK.value(), HttpStatus.OK, "우울증 테스트 답변 수정 완료", map);
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