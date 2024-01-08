package com.fcc.PureSync.context.sleep.controller;


import com.fcc.PureSync.common.ResultDto;
import com.fcc.PureSync.context.sleep.dto.SleepDto;
import com.fcc.PureSync.jwt.CustomUserDetails;
import com.fcc.PureSync.context.sleep.service.SleepService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sleep")
@RequiredArgsConstructor
public class SleepController {

    private final SleepService sleepService;

    @PostMapping
    public ResultDto createSleep(@RequestBody SleepDto sleepDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String id = customUserDetails.getUsername();
        return sleepService.createSleep(sleepDto, id);
    }

    @PutMapping("/{sleepSeq}")
    public ResultDto updateSleep(@PathVariable Long sleepSeq, @RequestBody SleepDto sleepDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String id = customUserDetails.getUsername();
        return sleepService.updateSleep(sleepSeq, sleepDto, id);
    }


    @GetMapping
    public ResultDto getAllMySleep(Pageable pageable , @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String id = customUserDetails.getUsername();
        return sleepService.findAllMySleep(pageable,id);

    }

    @DeleteMapping("/{sleepSeq}")
    public ResultDto deleteSleep(@PathVariable Long sleepSeq, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String id = customUserDetails.getUsername();
        return sleepService.deleteSleep(sleepSeq, id);
    }
}

