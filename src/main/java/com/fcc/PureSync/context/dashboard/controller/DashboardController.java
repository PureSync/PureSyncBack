package com.fcc.PureSync.context.dashboard.controller;

import com.fcc.PureSync.core.ResultDto;
import com.fcc.PureSync.core.exception.CustomException;
import com.fcc.PureSync.core.exception.CustomExceptionCode;
import com.fcc.PureSync.core.jwt.CustomUserDetails;
import com.fcc.PureSync.context.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping(value = {"/dashboard/dates", "/dashboard/dates/{baseDate}"})
    public ResultDto getDashboardInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable(required = false) String baseDate) {
        if (customUserDetails == null) {
            throw new CustomException(CustomExceptionCode.INVALID_JWT);
        }

        if (baseDate == null) {
            LocalDate localDate = LocalDate.now();
            baseDate = localDate.toString();
        }

        return dashboardService.getDashboardInfo(customUserDetails.getMemSeq(), baseDate);
    }

}
