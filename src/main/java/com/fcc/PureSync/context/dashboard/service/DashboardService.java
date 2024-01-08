package com.fcc.PureSync.context.dashboard.service;

import com.fcc.PureSync.context.dashboard.vo.DashboardDefaultNativeVo;
import com.fcc.PureSync.context.dashboard.vo.*;
import com.fcc.PureSync.common.ResultDto;
import com.fcc.PureSync.context.exercise.repository.ExerciseRepository;
import com.fcc.PureSync.context.sleep.repository.SleepRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ExerciseRepository exerciseRepository;
    private final SleepRepository sleepRepository;

    @Transactional
    public ResultDto getDashboardInfo(Long memSeq, String date) {
        HashMap<String, Object> data = new HashMap<>();

        List<DefaultChartVo> chartDataList = exerciseRepository.find7DaysChartData(memSeq, date);
        DashboardDefaultNativeVo defaultData = exerciseRepository.findDefaultData(memSeq, date);

        data.put("chart", chartDataList);
        data.put("default", defaultData);

        return ResultDto.of(HttpStatus.OK.value(), HttpStatus.OK, "성공", data);
    }

    @Transactional
    public ResultDto getExerciseChart(Long memSeq, String range, String date) {
        HashMap<String, Object> data = new HashMap<>();
        List<ExerciseStatsNativeVo> statsList = null;
        switch (range) {
            case "monthly" -> {
                statsList = exerciseRepository.findLastDaysExerciseStats(memSeq, date);
            }
            case "yearly" -> {
                statsList = exerciseRepository.findLastMonthsExerciseStats(memSeq, date);
            }
        }

        data.put(range, statsList);
        return ResultDto.of(HttpStatus.OK.value(), HttpStatus.OK, "성공", data);
    }

    @Transactional
    public ResultDto getSleepChart(Long memSeq, String range, String targetDate) {
        HashMap<String, Object> data = new HashMap<>();
        List<SleepStatsNativeVo> statsList = null;
        switch (range) {
            case "monthly" -> {
                statsList =  sleepRepository.findLastDaysSleepStats(memSeq, targetDate);
            }
            case "yearly" -> {
                statsList =  sleepRepository.findLastMonthsSleepStats(memSeq, targetDate);
            }
        }

        data.put(range, statsList);
        return ResultDto.of(HttpStatus.OK.value(), HttpStatus.OK, "성공", data);
    }


}