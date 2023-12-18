package com.fcc.PureSync.controller;

import com.fcc.PureSync.dto.ResultDto;
import com.fcc.PureSync.service.DashboardService;
import com.fcc.PureSync.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping(value = {"/dashboard/{memSeq}", "/dashboard/{memSeq}/{baseDate}"})
    public ResultDto getDashboardInfo(@PathVariable("memSeq") Long memSeq, @PathVariable(required = false) String baseDate)   {
        if (baseDate == null) {
            LocalDate localDate = LocalDate.now();
            baseDate = localDate.toString();
        }

        return dashboardService.getDashboardInfo(memSeq, baseDate);
    }

    @GetMapping("/positive")
    public ResultDto getPositive() {
        return dashboardService.getRandomPositive();
    }


}
