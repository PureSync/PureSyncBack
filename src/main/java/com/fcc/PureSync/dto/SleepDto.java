package com.fcc.PureSync.dto;

import com.fcc.PureSync.entity.Board;
import com.fcc.PureSync.entity.Sleep;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SleepDto {

    private Long sleepSeq;
    private LocalDateTime sleepWdate;

    private LocalDateTime sleepGodate;
    private LocalDateTime sleepWudate;
    private Long memSeq;
    private Integer sleepColor;
    private Integer sleepCategory;
    private String sleepDate;

    public static SleepDto toDto(Sleep sleep) {
        return SleepDto.builder()
                .sleepSeq(sleep.getSleepSeq())
                .sleepWdate(sleep.getSleepWdate())
                .sleepGodate(sleep.getSleepGodate())
                .sleepWudate(sleep.getSleepWudate())
                .memSeq(sleep.getMember().getMemSeq())
                .sleepCategory(sleep.getSleepCategory())
                .sleepColor(sleep.getSleepColor())
                .sleepDate(sleep.getSleepDate())
                .build();
    }
}

