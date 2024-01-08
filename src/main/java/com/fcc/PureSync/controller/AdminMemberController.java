package com.fcc.PureSync.controller;

import com.fcc.PureSync.dto.AdminMemberDto;
import com.fcc.PureSync.dto.MemberDetailDto;
import com.fcc.PureSync.core.ResultDto;
import com.fcc.PureSync.entity.MemberSearchCondition;
import com.fcc.PureSync.context.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/admin")
@RequiredArgsConstructor
//@RestController
@Controller
public class AdminMemberController {
    private final MemberService memberService;


    @GetMapping("/member/list")
    public String adminMemberList(MemberSearchCondition condition, Pageable pageable, Model model) {

        Page<AdminMemberDto> results = memberService.getMembers(condition, pageable);
        model.addAttribute("memberList", results);

        // 필터영역 설정
        setFilterInfo(condition, model);

        // 페이지 정보 설정
        setPageInfo(results, model);

        return "users/memberList";
    }

    private void setPageInfo(Page<AdminMemberDto> results, Model model) {
        model.addAttribute("totalCount", results.getTotalElements());
        model.addAttribute("size",  results.getPageable().getPageSize());
        model.addAttribute("number",  results.getPageable().getPageNumber());
        model.addAttribute("maxPage", 5);
    }

    private void setFilterInfo(MemberSearchCondition condition, Model model) {
        model.addAttribute("keyword", condition.getKeyword());
        model.addAttribute("category", condition.getCategory());
        model.addAttribute("status", condition.getStatus());
    }

    /*
    * 회원목록 게시판 상세조회
    * */
    @GetMapping("/member/{seq}")
    public String adminMemberDetails(@PathVariable("seq") Long seq, Model model) {
        MemberDetailDto result = memberService.getMemberDetail(seq);
        model.addAttribute("memberDetail", result);
        return "users/memberView";
    }

    /*
    * 회원정보 업데이트 (어드민)
    * */
    @PostMapping("/member/{seq}")
    @ResponseBody
    public ResultDto adminMemberUpdate(@PathVariable("seq") Long seq, @RequestBody Map<String, Integer> requestBody) {
        int statusCode = requestBody.get("statusCode");
        return memberService.updateStatus(seq, statusCode);
    }

}
