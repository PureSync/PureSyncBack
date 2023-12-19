package com.fcc.PureSync.controller;

import com.fcc.PureSync.dto.MenuDto;
import com.fcc.PureSync.dto.ResultDto;
import com.fcc.PureSync.dto.SummaryDto;
import com.fcc.PureSync.entity.Menu;
import com.fcc.PureSync.jwt.CustomUserDetails;
import com.fcc.PureSync.service.MenuService;
import com.fcc.PureSync.service.SummaryService;
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
        Long memSeq = customUserDetails.getMemSeq();

        map.put("el_date", req.getParameter("el_date"));
        map.put("menu_date", req.getParameter("menu_date"));

        return summaryService.getBodyBase(map, memSeq);
    }

}
