package com.fcc.PureSync.context.sleep.service;

import com.fcc.PureSync.common.ResultDto;
import com.fcc.PureSync.context.board.service.CommentService;
import com.fcc.PureSync.context.sleep.dto.SleepDto;
import com.fcc.PureSync.context.sleep.entity.Sleep;
import com.fcc.PureSync.context.sleep.repository.SleepRepository;
import com.fcc.PureSync.entity.Member;
import com.fcc.PureSync.exception.CustomException;
import com.fcc.PureSync.exception.CustomExceptionCode;
import com.fcc.PureSync.exception.ErrorResponse;
import com.fcc.PureSync.repository.MemberRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;


import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

class SleepServiceTest {

    @Autowired
    private SleepService sleepService;

    MemberRepository memberRepository = mock(MemberRepository.class);

    SleepRepository sleepRepository = mock(SleepRepository.class);

    @InjectMocks
    private final Member mockMem = Member.builder()
            .memId("user")
            .memNick("aaa")
            .build();

    private final Sleep mockSleep = Sleep.builder()
            .sleepGodate(LocalDateTime.parse("2023-12-20T00:00:00"))
            .sleepWudate(LocalDateTime.parse("2023-12-21T00:00:00"))
            .member(mockMem)
            .sleepCategory(1)
            .build();
    private final Sleep mockSleep2 = Sleep.builder()
            .sleepGodate(LocalDateTime.parse("2023-12-22T00:00:00"))
            .sleepWudate(LocalDateTime.parse("2023-12-23T00:00:00"))
            .member(mockMem)
            .sleepCategory(1)
            .build();
    private void assertEquals(CustomExceptionCode customExceptionCode, CustomExceptionCode exceptionCode) {
    }
    @BeforeEach
    void setUp(){
        sleepService = new SleepService(memberRepository,sleepRepository);
    }

    @Test
    @DisplayName("수면 기록 등록 성공")
    void createSleep_success() {
        SleepDto sleepDto = SleepDto.builder()
                .sleepGodate(LocalDateTime.parse("2023-12-20T00:00:00"))
                .sleepWudate(LocalDateTime.parse("2023-12-21T00:00:00"))
                .memSeq(mockMem.getMemSeq())
                .sleepCategory(1)
                .build();
        Sleep mockSleep = mock(Sleep.class);

        when(memberRepository.findByMemId(mockMem.getMemId()))
                .thenReturn(of(mockMem));

        when(sleepRepository.save(any(Sleep.class)))
                .thenReturn(mockSleep);

        Assertions.assertDoesNotThrow(()-> sleepService.createSleep(sleepDto,mockMem.getMemId()));

    }
    @Test
    @DisplayName("수면 기록 등록 실패 (존재하는 유저가 없는 경우)")
    void createSleep_NotFoundUser() {
        SleepDto sleepDto = SleepDto.builder()
                .sleepGodate(LocalDateTime.parse("2023-12-20T00:00:00"))
                .sleepWudate(LocalDateTime.parse("2023-12-21T00:00:00"))
                .memSeq(mockMem.getMemSeq())
                .sleepCategory(1)
                .build();
        when(memberRepository.findByMemId(mockMem.getMemId()))
                .thenReturn(Optional.empty());
        when(sleepRepository.save(any(Sleep.class)))
                .thenReturn(mockSleep);
        CustomException customException = assertThrows(CustomException.class,
                () -> sleepService.createSleep(sleepDto,mockMem.getMemId()));

        assertEquals(CustomExceptionCode.NOT_FOUND_USER, customException.getExceptionCode());

    }
    @Test
    @DisplayName("수면 기록 수정 성공")
    void updateSleep_success() {
        SleepDto sleepDto = SleepDto.builder()
                .sleepGodate(LocalDateTime.parse("2023-12-20T00:00:00"))
                .sleepWudate(LocalDateTime.parse("2023-12-21T00:00:20"))
                .memSeq(mockMem.getMemSeq())
                .sleepCategory(1)
                .build();

        when(memberRepository.findByMemId(mockMem.getMemId()))
                .thenReturn(of(mockMem));
        when(sleepRepository.findById(mockSleep.getSleepSeq()))
                .thenReturn(of(mockSleep));
        when(sleepRepository.save(any(Sleep.class)))
                .thenReturn(mockSleep);

        Assertions.assertDoesNotThrow(()-> sleepService.updateSleep(sleepDto.getSleepSeq(),sleepDto,mockMem.getMemId()));
    }
    
    @Test
    @DisplayName("수면 기록 수정 실패 (존재하는 유저가 없는 경우)")
    void updateSleep_NotFoundUser() {
        SleepDto sleepDto = SleepDto.builder()
                .sleepGodate(LocalDateTime.parse("2023-12-20T00:00:00"))
                .sleepWudate(LocalDateTime.parse("2023-12-21T00:00:00"))
                .memSeq(mockMem.getMemSeq())
                .sleepCategory(1)
                .build();

        when(memberRepository.findByMemId(mockMem.getMemId()))
                .thenReturn(Optional.empty());
        when(sleepRepository.findById(mockSleep.getSleepSeq()))
                .thenReturn(of(mockSleep));
        when(sleepRepository.save(any(Sleep.class)))
                .thenReturn(mockSleep);
        CustomException customException = assertThrows(CustomException.class,
                () -> sleepService.updateSleep(mockSleep.getSleepSeq(),sleepDto, mockMem.getMemId()));

        assertEquals(CustomExceptionCode.NOT_FOUND_USER, customException.getExceptionCode());

    }
    @Test
    @DisplayName("수면 기록 수정 실패 (존재하는 수면 기록이 없는 경우)")
    void updateSleep_NotFoundSleep() {
        SleepDto sleepDto = SleepDto.builder()
                .sleepGodate(LocalDateTime.parse("2023-12-20T00:00:00"))
                .sleepWudate(LocalDateTime.parse("2023-12-21T00:00:00"))
                .memSeq(mockMem.getMemSeq())
                .sleepCategory(1)
                .build();

        when(memberRepository.findByMemId(mockMem.getMemId()))
                .thenReturn(of(mockMem));
        when(sleepRepository.findById(mockSleep.getSleepSeq()))
                .thenReturn(Optional.empty());
        when(sleepRepository.save(any(Sleep.class)))
                .thenReturn(mockSleep);
        CustomException customException = assertThrows(CustomException.class,
                () -> sleepService.updateSleep(mockSleep.getSleepSeq(),sleepDto, mockMem.getMemId()));

        assertEquals(CustomExceptionCode.NOT_FOUND_SLEEP, customException.getExceptionCode());
    }
    @Test
    @DisplayName("수면 기록 조회 성공")
    void findAllMySleep_success() {
        when(memberRepository.findByMemId(mockMem.getMemId()))
                .thenReturn(of(mockMem));
        Pageable pageable = mock(Pageable.class);
        List<Sleep> sleepList = Arrays.asList(mockSleep,mockSleep2);
        when(sleepRepository.findByMember_MemSeq(mockMem.getMemSeq()))
                .thenReturn(sleepList);

        List<SleepDto> sleepDtoList = sleepList.stream()
                .map(SleepDto::toDto)
                .toList();

        Assertions.assertDoesNotThrow(()->sleepService.findAllMySleep(pageable,mockMem.getMemId()));
        assertThat(sleepDtoList).hasSize(2);
        assertThat(sleepDtoList.get(0).getSleepGodate().isEqual(LocalDateTime.parse("2023-12-20T00:00:00"))).isTrue();
        assertThat(sleepDtoList.get(1).getSleepGodate().isEqual(LocalDateTime.parse("2023-12-22T00:00:00"))).isTrue();
    }

    @Test
    @DisplayName("수면 기록 삭제 성공")
    void deleteSleep_success() {
        when(memberRepository.findByMemId(mockMem.getMemId()))
                .thenReturn(of(mockMem));

        when(sleepRepository.findById(mockSleep.getSleepSeq()))
                .thenReturn(of(mockSleep));

        sleepRepository.delete(mockSleep);

        Assertions.assertDoesNotThrow(()->sleepService.deleteSleep(mockSleep.getSleepSeq(),mockMem.getMemId()));
    }
}