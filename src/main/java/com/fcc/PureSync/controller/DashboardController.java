package com.fcc.PureSync.controller;

import com.fcc.PureSync.common.HeaderInfo;
import com.fcc.PureSync.dto.ResultDto;
import com.fcc.PureSync.jwt.CustomUserDetails;
import com.fcc.PureSync.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpEntity;

import java.time.LocalDate;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping(value = {"/dashboard", "/dashboard/{baseDate}"})
    public ResultDto getDashboardInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable(required = false) String baseDate) {
        if (baseDate == null) {
            LocalDate localDate = LocalDate.now();
            baseDate = localDate.toString();
        }

        return dashboardService.getDashboardInfo(customUserDetails.getMemSeq(), baseDate);
    }

    @GetMapping("/positive")
    public ResultDto getPositive() {
        return dashboardService.getRandomPositive();
    }


}
