package com.fcc.PureSync.context.summary.controller;


import com.fcc.PureSync.core.ResultDto;
import com.fcc.PureSync.core.jwt.CustomUserDetails;
import com.fcc.PureSync.core.exception.CustomException;
import com.fcc.PureSync.core.exception.CustomExceptionCode;

import com.fcc.PureSync.context.summary.service.SummaryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping( value = "/api/summary" )
@RequiredArgsConstructor
public class SummaryController {

    private final SummaryService summaryService;

    @GetMapping("/list")
    public ResultDto  getBodyBase (HashMap<String,String> map, HttpServletRequest req, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails == null) {
            throw new CustomException(CustomExceptionCode.INVALID_JWT);
        }
        Long memSeq = customUserDetails.getMemSeq();
        map.put("el_date", req.getParameter("el_date"));
        map.put("menu_date", req.getParameter("menu_date"));

        return summaryService.getBodyBase(map, memSeq);
    }

}
