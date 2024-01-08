package com.fcc.PureSync.core.util;

import com.fcc.PureSync.context.trash.service.MdTrashService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class Scheduler {

    private final MdTrashService mdTrashService;

    //초 분 시 일 월 요일
    @Scheduled(cron = "0 0 0 * * *" )
    public void deleteYesterdayMdTrashes() {
        mdTrashService.deleteYesterdayMdTrashes();
    }
}
