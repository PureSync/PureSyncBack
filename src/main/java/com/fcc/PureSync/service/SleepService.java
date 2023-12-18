package com.fcc.PureSync.service;

import com.fcc.PureSync.dto.CommentDto;
import com.fcc.PureSync.dto.ResultDto;
import com.fcc.PureSync.dto.SleepDto;
import com.fcc.PureSync.entity.Comment;
import com.fcc.PureSync.entity.Member;
import com.fcc.PureSync.entity.Sleep;
import com.fcc.PureSync.exception.CustomException;
import com.fcc.PureSync.exception.CustomExceptionCode;
import com.fcc.PureSync.repository.MemberRepository;
import com.fcc.PureSync.repository.SleepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static com.fcc.PureSync.dto.SleepDto.toDto;

@Service
@RequiredArgsConstructor
public class SleepService {

    private final MemberRepository memberRepository;
    private final SleepRepository sleepRepository;

    public ResultDto buildResultDto(int code, HttpStatus status, String msg, HashMap<String, Object> map) {
        return ResultDto.builder()
                .code(code)
                .httpStatus(status)
                .message(msg)
                .data(map)
                .build();
    }


    // 쓰기 -----------------------------------------------------------
    public ResultDto createSleep(SleepDto sleepDto, String id) {
        id = "aaa";//////////////////////////////////////////////
        Member member = memberRepository.findByMemId(id)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));
        LocalDateTime godate = sleepDto.getSleepGodate().withNano(0);
        LocalDateTime wudate = sleepDto.getSleepWudate().withNano(0);

        Sleep sleep = Sleep.builder()
                .sleepGodate(godate)
                .sleepWudate(wudate)
                .sleepCategory(sleepDto.getSleepCategory())
                .sleepColor(sleepDto.getSleepColor())
                .sleepDate(sleepDto.getSleepDate())
                .member(member)
                .build();

        sleepRepository.save(sleep);
        SleepDto dto = toDto(sleep);
        HashMap<String, Object> map = new HashMap<>();
        map.put("sleep", dto);
        return buildResultDto(HttpStatus.CREATED.value(), HttpStatus.CREATED, "수면 기록 생성 성공", map);
    }

    // 업데이트 ------------------------------------------------------------------------
    public ResultDto updateSleep(Long sleepSeq, SleepDto sleepDto, String id) {
        id = "aaa";//////////////////////////////////////////////
        Member member = memberRepository.findByMemId(id)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));
        Sleep sleep = sleepRepository.findById(sleepSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_SLEEP));
        LocalDateTime godate = sleepDto.getSleepGodate().withNano(0);
        LocalDateTime wudate = sleepDto.getSleepWudate().withNano(0);

        Sleep updateSleep = Sleep.builder()
                .sleepSeq(sleepSeq)
                .sleepGodate(godate)
                .sleepWudate(wudate)
                .sleepCategory(sleepDto.getSleepCategory())
                .sleepColor(sleep.getSleepColor())
                .sleepDate(sleep.getSleepDate())
                .member(member)
                .build();

        sleepRepository.save(updateSleep);
        SleepDto dto = toDto(updateSleep);
        HashMap<String, Object> map = new HashMap<>();
        map.put("updateSleep", dto);
        return buildResultDto(HttpStatus.CREATED.value(), HttpStatus.CREATED, "수면 기록 수정 성공", map);
    }


    public ResultDto findAllMySleep(Pageable pageable, String id) {
        id = "aaa";
        Member member = memberRepository.findByMemId(id)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));
        List<Sleep> sleepList = sleepRepository.findByMember_MemSeq(member.getMemSeq());
        List<SleepDto> sleepDtoList = sleepList.stream()
                .map(SleepDto::toDto)
                .toList();
        HashMap<String, Object> map = new HashMap<>();
        map.put("sleepList", sleepDtoList);
        return buildResultDto(HttpStatus.OK.value(), HttpStatus.OK, "수면기록 전체 조회 성공", map);

    }

    public ResultDto deleteSleep(Long sleepSeq, String id) {
        id = "aaa";
        Member member = memberRepository.findByMemId(id)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));
        Sleep sleep = sleepRepository.findById(sleepSeq)
                .orElseThrow(()->new CustomException(CustomExceptionCode.NOT_FOUND_SLEEP));

        sleepRepository.delete(sleep);

        return buildResultDto(HttpStatus.OK.value(), HttpStatus.OK, "수면기록 삭제 성공", null);
    }
}