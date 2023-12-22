package com.fcc.PureSync.controller;

import com.fcc.PureSync.dto.LoginDto;
import com.fcc.PureSync.entity.Member;
import com.fcc.PureSync.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@ToString
@RequiredArgsConstructor
@Controller
public class AdminController {
    private final AdminService adminService;
    /*
    메인 페이지 Include를 찾지 못하여 주소 변경을 못함...
    유저 정보 꺼내오는 방법
    @AuthenticationPrincipal CustomUserDetails customUserDetails
    customUserDetails.get 하면
    만들어 놓은 메소드로 memSeq memId memEmail을 가져올 수 있습니다.
    */
    //로그인 페이지
    @GetMapping("/admin/login")
    public String adminMain() {
        return "login/login";
    }

//    //로그인 기능
//    @PostMapping("/member/login")
//    public String adminLogin(Model model, @ModelAttribute LoginDto loginDto) {
//        System.out.println("adminLogin"+loginDto);
//        Member member= adminService.adminLogin(loginDto);
//        model.addAttribute("adminInfo",member);
//        return "/";
//    }


}
