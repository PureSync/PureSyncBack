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

    @GetMapping(value = {"/dashboard/dates", "/dashboard/dates/{targetDate}"})
    public ResultDto getDashboardInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable(required = false) String targetDate) {
        if (customUserDetails == null) {
            throw new CustomException(CustomExceptionCode.INVALID_JWT);
        }

        if (targetDate == null) {
            LocalDate localDate = LocalDate.now();
            targetDate = localDate.toString();
        }

        return dashboardService.getDashboardInfo(customUserDetails.getMemSeq(), targetDate);
    }

    @GetMapping(value = {"/dashboard/dates/exercise/{range}", "/dashboard/dates/{targetDate}/exercise/{range}"})
    public ResultDto getExerciseChart(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String range, @PathVariable(required = false) String targetDate) {

        if (customUserDetails == null) {
            throw new CustomException(CustomExceptionCode.INVALID_JWT);
        }

        if (targetDate == null) {
            LocalDate localDate = LocalDate.now();
            targetDate = localDate.toString();
        }

        return dashboardService.getExerciseChart(customUserDetails.getMemSeq(), range, targetDate);
    }

    @GetMapping(value = {"/dashboard/dates/sleep/{range}", "/dashboard/dates/{targetDate}/sleep/{range}"})
    public ResultDto getDashboardDetail(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String range, @PathVariable(required = false) String targetDate) {

        if (customUserDetails == null) {
            throw new CustomException(CustomExceptionCode.INVALID_JWT);
        }

        if (targetDate == null) {
            LocalDate localDate = LocalDate.now();
            targetDate = localDate.toString();
        }

        return dashboardService.getSleepChart(customUserDetails.getMemSeq(), range, targetDate);
    }


}
