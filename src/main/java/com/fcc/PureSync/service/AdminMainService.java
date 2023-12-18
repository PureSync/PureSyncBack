package com.fcc.PureSync.service;

import com.fcc.PureSync.repository.MemberRepository;
import com.fcc.PureSync.repository.VisitorRepository;
import com.fcc.PureSync.vo.CountInfoNativeVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminMainService {
    private final VisitorRepository visitorRepository;
    private final MemberRepository memberRepository;

    public Integer getTodayVisitorCount(String date) {

        return visitorRepository.countByVisitorDate(date);
    }

    public Integer getVisitorDifference(String date) {
        String[] splitDate = date.split("-");
        int year = Integer.parseInt(splitDate[0]);
        int month = Integer.parseInt(splitDate[1]);
        int day = Integer.parseInt(splitDate[2]);
        // 현재 날짜를 가져옴
        LocalDate today = LocalDate.of(year, month, day);
        LocalDate yesterday = today.minusDays(1);

        // 원하는 형식으로 날짜를 포맷
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = yesterday.format(formatter);

        Integer todayCount = getTodayVisitorCount(date);
        Integer yesterdayCount = visitorRepository.countByVisitorDate(formattedDate);

        return todayCount - yesterdayCount;

    }
    public Integer getTodayRegisterMemberCount(String date) {
        String[] splitDate = date.split("-");
        int year = Integer.parseInt(splitDate[0]);
        int month = Integer.parseInt(splitDate[1]);
        int day = Integer.parseInt(splitDate[2]);

        // LocalDateTime 객체를 생성할 때 년, 월, 일 정보를 사용하여 만듦
        LocalDateTime today = LocalDateTime.of(year, month, day, 0, 0);

        // 자정으로 설정
        LocalDateTime startDateTime= today.withHour(0).withMinute(0).withSecond(0).withNano(0);

        //23시 59분 59초로 설정
        LocalDateTime endDateTime = today.withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        return memberRepository.countByMemCreatedAtBetween(startDateTime, endDateTime);
    }

    public Integer getRegisterMemberDifference(String date) {
        String[] splitDate = date.split("-");
        int year = Integer.parseInt(splitDate[0]);
        int month = Integer.parseInt(splitDate[1]);
        int day = Integer.parseInt(splitDate[2]);

        // LocalDateTime 객체를 생성할 때 년, 월, 일 정보를 사용하여 만듦
        LocalDateTime today = LocalDateTime.of(year, month, day, 0, 0);

        LocalDateTime yesterday = today.minusDays(1);

        // 자정으로 설정
        LocalDateTime startDateTime= yesterday.withHour(0).withMinute(0).withSecond(0).withNano(0);

        //23시 59분 59초로 설정
        LocalDateTime endDateTime = yesterday.withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        Integer todayCount = getTodayRegisterMemberCount(date);
        Integer yesterdayCount = memberRepository.countByMemCreatedAtBetween(startDateTime, endDateTime);

        return todayCount - yesterdayCount;
    }

    public Integer getTodayWithdrawnMemberCount(String date) {
        String[] splitDate = date.split("-");
        int year = Integer.parseInt(splitDate[0]);
        int month = Integer.parseInt(splitDate[1]);
        int day = Integer.parseInt(splitDate[2]);

        // LocalDateTime 객체를 생성할 때 년, 월, 일 정보를 사용하여 만듦
        LocalDateTime today = LocalDateTime.of(year, month, day, 0, 0);

        // 자정으로 설정
        LocalDateTime startDateTime= today.withHour(0).withMinute(0).withSecond(0).withNano(0);

        //23시 59분 59초로 설정
        LocalDateTime endDateTime = today.withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        return memberRepository.countByMemStatusAndMemUpdatedAtBetween(2, startDateTime, endDateTime);
    }

    public Integer getWithdrawnMemberDifference(String date) {
        String[] splitDate = date.split("-");
        int year = Integer.parseInt(splitDate[0]);
        int month = Integer.parseInt(splitDate[1]);
        int day = Integer.parseInt(splitDate[2]);

        // LocalDateTime 객체를 생성할 때 년, 월, 일 정보를 사용하여 만듦
        LocalDateTime today = LocalDateTime.of(year, month, day, 0, 0);
        LocalDateTime yesterday = today.minusDays(1);

        // 자정으로 설정
        LocalDateTime startDateTime= yesterday.withHour(0).withMinute(0).withSecond(0).withNano(0);

        //23시 59분 59초로 설정
        LocalDateTime endDateTime = yesterday.withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        Integer todayCount = getTodayWithdrawnMemberCount(date);
        Integer yesterdayCount = memberRepository.countByMemStatusAndMemUpdatedAtBetween(2, startDateTime, endDateTime);

        return todayCount - yesterdayCount;
    }

    public List<String> getVisitorDateList(String date) {
        List<CountInfoNativeVo> list = visitorRepository.getWeeklyVisitor(date);
        List<String> dateArray = list.stream().map(e -> e.getDate()).toList();

        return dateArray;
    }


    public List<Integer> getVisitorCountList(String date) {
        List<CountInfoNativeVo> list = visitorRepository.getWeeklyVisitor(date);
        List<Integer> countArray = list.stream().map(e -> e.getCount()).toList();

        return countArray;
    }

    public List<Integer> getRegisterMemberCountList(String date) {
        String[] splitDate = date.split("-");
        int year = Integer.parseInt(splitDate[0]);
        int month = Integer.parseInt(splitDate[1]);
        int day = Integer.parseInt(splitDate[2]);

        // LocalDateTime 객체를 생성할 때 년, 월, 일 정보를 사용하여 만듦
        LocalDateTime today = LocalDateTime.of(year, month, day, 0, 0);

        //23시 59분 59초로 설정
        LocalDateTime todayTime = today.withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        List<CountInfoNativeVo> list =  memberRepository.countByMemberCreatedAt(todayTime);
        List<Integer> registerMemberCount = list.stream().map(e -> e.getCount()).toList();

        return registerMemberCount;
    }

    public List<Integer> getWithdrawnMemberCountList(String date) {
        String[] splitDate = date.split("-");
        int year = Integer.parseInt(splitDate[0]);
        int month = Integer.parseInt(splitDate[1]);
        int day = Integer.parseInt(splitDate[2]);

        // LocalDateTime 객체를 생성할 때 년, 월, 일 정보를 사용하여 만듦
        LocalDateTime today = LocalDateTime.of(year, month, day, 0, 0);

        //23시 59분 59초로 설정
        LocalDateTime todayTime = today.withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        List<CountInfoNativeVo> list =  memberRepository.countByMemberUpdatedAt(todayTime);
        List<Integer> withdrawnMemberCount = list.stream().map(e -> e.getCount()).toList();

        return withdrawnMemberCount;
    }





}
