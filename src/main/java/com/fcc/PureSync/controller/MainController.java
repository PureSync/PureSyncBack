package com.fcc.PureSync.controller;

import com.fcc.PureSync.dto.ResultDto;
import com.fcc.PureSync.service.VisitorService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/visitor")
@RequiredArgsConstructor
public class MainController {
    private final VisitorService visitorService;
    @PostMapping
    public ResultDto addVisitor(HttpServletRequest request){
        return visitorService.addVisitor(request);
    }

}
