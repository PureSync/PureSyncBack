package com.fcc.PureSync.context.admin.notice.controller;

import com.fcc.PureSync.core.Pager;
import com.fcc.PureSync.context.admin.notice.dto.NoticeDto;
import com.fcc.PureSync.core.ResultDto;
import com.fcc.PureSync.context.admin.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminNoticeController {

    private final NoticeService noticeService;

    @GetMapping("/admin/notice/list/{pg}")
    public String adminBoardList(Model model, NoticeDto noticeDto , @PathVariable("pg") int pg) {
        String searchText = URLDecoder.decode( noticeDto.getSearchText(), StandardCharsets.UTF_8 );
        if( searchText == null ) {
            searchText = " ";
        }

        noticeDto.setSearchText(searchText);
        noticeDto.setStart(pg*10);
        List<NoticeDto> noticeList = noticeService.getAllNoticeList(noticeDto);

        int total = noticeService.getNoticeTotalcnt(noticeDto);

        model.addAttribute("noticeList", noticeList);
        model.addAttribute("page", Pager.makePage(10, total , pg));
        model.addAttribute("pg", pg );
        model.addAttribute("total", total );

        return "adminBoard/noticeList";
    }

    @ResponseBody
    @GetMapping("/api/notice/list")
    public ResultDto getNoticeListTopThree (NoticeDto noticeDto) {
        return noticeService.getNoticeListTopThree(noticeDto);
    }

    @ResponseBody
    @GetMapping("/api/notice/view/{notice_seq}")
    public ResultDto getNoticeView (@PathVariable Long notice_seq) {
        NoticeDto noticeViewDto = new NoticeDto();
        noticeViewDto.setNotice_seq(notice_seq);
        return noticeService.detailNotice(noticeViewDto);
    }

    @GetMapping("/admin/notice/view/{notice_seq}")
    public String adminBoardView(Model model, @PathVariable("notice_seq") long notice_seq) {
        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setNotice_seq(notice_seq);
        NoticeDto resultDto = noticeService.noticeBoardView(noticeDto);
        model.addAttribute("noticeView", resultDto);
        return "adminBoard/noticeView";
    }

    @GetMapping("/admin/notice/write")
    public String adminBoardWrite() {
        return "adminBoard/noticeWrite";
    }

    @PostMapping("/admin/notice/save")
    @ResponseBody
    public HashMap<String, Object> adminBoardSave( @RequestBody NoticeDto noticeDto) {
        noticeService.noticeBoardWrite(noticeDto);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "success");

        return resultMap;
    }

    @GetMapping("/admin/notice/modify/{notice_seq}")
    public String adminBoardModify(Model model, @PathVariable("notice_seq") long notice_seq) {
        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setNotice_seq(notice_seq);
        NoticeDto resultDto = noticeService.noticeBoardView(noticeDto);
        model.addAttribute("noticeModify", resultDto);
        return "adminBoard/noticeModify";
    }

    @PostMapping("/admin/notice/modifyOk")
    @ResponseBody
    public HashMap<String, Object> adminBoardModifyOk( @RequestBody NoticeDto noticeDto) {
        noticeService.noticeBoardUpdate(noticeDto);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "success");

        return resultMap;
    }

    @GetMapping("/admin/notice/delete/{notice_seq}")
    @ResponseBody
    public HashMap<String, Object> noticeBoardDelete( NoticeDto noticeDto) {
        noticeService.noticeBoardDelete(noticeDto);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        return  resultMap;
    }


}
