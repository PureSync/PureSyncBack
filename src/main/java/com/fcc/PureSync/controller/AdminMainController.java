package com.fcc.PureSync.controller;

import com.fcc.PureSync.service.AdminMainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminMainController {

    final AdminMainService adminMainService;

    @GetMapping("/")
    public String adminMain(Model model) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = today.format(formatter);

        Integer todayVisitorCount = adminMainService.getTodayVisitorCount(formattedDate);
        Integer visitorDifference = adminMainService.getVisitorDifference(formattedDate);
        Integer todayRegisterMemberCount = adminMainService.getTodayRegisterMemberCount(formattedDate);
        Integer registerMemberDifference = adminMainService.getRegisterMemberDifference(formattedDate);
        Integer todayWithdrawnMemberCount = adminMainService.getTodayWithdrawnMemberCount(formattedDate);
        Integer withdrawnMemberDifference = adminMainService.getWithdrawnMemberDifference(formattedDate);
        List<String> dateList = adminMainService.getVisitorDateList(formattedDate);
        List<Integer> countList = adminMainService.getVisitorCountList(formattedDate);
        List<Integer> registerMemberCountList = adminMainService.getRegisterMemberCountList(formattedDate);
        List<Integer> withdrawnMemberCountList = adminMainService.getWithdrawnMemberCountList(formattedDate);

        model.addAttribute("visitorDifference", visitorDifference);
        model.addAttribute("todayVisitorCount", todayVisitorCount);
        model.addAttribute("todayRegisterMemberCount", todayRegisterMemberCount);
        model.addAttribute("registerMemberDifference", registerMemberDifference);
        model.addAttribute("todayWithdrawnMemberCount", todayWithdrawnMemberCount);
        model.addAttribute("withdrawnMemberDifference", withdrawnMemberDifference);
        model.addAttribute("dateList", dateList);
        model.addAttribute("countList", countList);
        model.addAttribute("registerMemberCountList", registerMemberCountList);
        model.addAttribute("withdrawnMemberCountList", withdrawnMemberCountList);

        return "index";

    }

    @GetMapping("/admin/dashboard/{date}")
    @ResponseBody
    public HashMap<String, Object> adminDashboard(@PathVariable String date) {
        HashMap<String, Object> map = new HashMap<>();

        Integer todayVisitorCount = adminMainService.getTodayVisitorCount(date);
        Integer visitorDifference = adminMainService.getVisitorDifference(date);
        Integer todayRegisterMemberCount = adminMainService.getTodayRegisterMemberCount(date);
        Integer registerMemberDifference = adminMainService.getRegisterMemberDifference(date);
        Integer todayWithdrawnMemberCount = adminMainService.getTodayWithdrawnMemberCount(date);
        Integer withdrawnMemberDifference = adminMainService.getWithdrawnMemberDifference(date);
        List<String> dateList = adminMainService.getVisitorDateList(date);
        List<Integer> countList = adminMainService.getVisitorCountList(date);
        List<Integer> registerMemberCountList = adminMainService.getRegisterMemberCountList(date);
        List<Integer> withdrawnMemberCountList = adminMainService.getWithdrawnMemberCountList(date);


        map.put("todayVisitorCount", todayVisitorCount);
        map.put("visitorDifference", visitorDifference);
        map.put("todayRegisterMemberCount", todayRegisterMemberCount);
        map.put("registerMemberDifference", registerMemberDifference);
        map.put("todayWithdrawnMemberCount", todayWithdrawnMemberCount);
        map.put("withdrawnMemberDifference", withdrawnMemberDifference);
        map.put("dateList", dateList);
        map.put("countList", countList);
        map.put("registerMemberCountList", registerMemberCountList);
        map.put("withdrawnMemberCountList", withdrawnMemberCountList);

        return map;
    }
}
