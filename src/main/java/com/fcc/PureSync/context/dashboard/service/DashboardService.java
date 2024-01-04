package com.fcc.PureSync.context.dashboard.service;

import com.fcc.PureSync.context.dashboard.vo.DashboardDefaultNativeVo;
import com.fcc.PureSync.context.dashboard.vo.*;
import com.fcc.PureSync.common.ResultDto;
import com.fcc.PureSync.context.exercise.repository.ExerciseRepository;
import com.fcc.PureSync.repository.SleepRepository;
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
    public ResultDto getDashboardDetail(Long memSeq, String type, String target, String date) {
        HashMap<String, Object> data = new HashMap<>();
        List<?> statsList = null;

        if (type.equals("exercise") && target.equals("monthly")) {
            statsList =  exerciseRepository.findLastDaysExerciseStats(memSeq, date, 30);

        } else if (type.equals("exercise") && target.equals("yearly")) {
            statsList =  exerciseRepository.findLastMonthsExerciseStats(memSeq, date, 12);

        } else if (type.equals("sleep") && target.equals("monthly")) {
            statsList =  sleepRepository.findLastDaysSleepStats(memSeq, date, 30);

        } else if (type.equals("sleep") && target.equals("yearly")) {
            statsList =  sleepRepository.findLastMonthsSleepStats(memSeq, date, 12);
        }

        data.put(type, statsList);

        return ResultDto.of(HttpStatus.OK.value(), HttpStatus.OK, "성공", data);
    }

}