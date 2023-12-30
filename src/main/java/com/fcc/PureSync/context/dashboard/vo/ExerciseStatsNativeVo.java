package com.fcc.PureSync.context.dashboard.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface ExerciseStatsNativeVo {
    String getDate();
    int getTotalTime();
    Double getTotalKcal();
}
