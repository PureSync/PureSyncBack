package com.fcc.PureSync.controller;

import com.fcc.PureSync.common.Pager;
import com.fcc.PureSync.dto.AdminQnaBoardDto;
import com.fcc.PureSync.service.AdminQnaBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminQnaBoardController {

    private final AdminQnaBoardService adminQnaBoardService;

    @GetMapping("/admin/qna/list/{pg}")
    public String qnaBoardList(Model model, AdminQnaBoardDto adminQnaBoardDto, @PathVariable("pg") int pg) {
        String searchText = URLDecoder.decode( adminQnaBoardDto.getSearchText() );
        if( searchText == null ) {
            searchText = " ";
        }
        adminQnaBoardDto.setSearchText(searchText);
        adminQnaBoardDto.setStart(pg * 10);
        List<AdminQnaBoardDto> qnaBoardList = adminQnaBoardService.getAllQnaBoardList(adminQnaBoardDto);

        model.addAttribute("page", Pager.makePage(10, adminQnaBoardService.getQnaBoardTotalcnt(adminQnaBoardDto), pg));
        model.addAttribute("qnaBoardList", qnaBoardList);
        model.addAttribute("pg", pg );
        return "/qnaBoard/qnaUserList";
    }


    @GetMapping("/admin/qna/view/{qna_board_seq}")
    public String qnaBoardView(Model model, @PathVariable("qna_board_seq") long qna_board_seq) {
        AdminQnaBoardDto adminQnaBoardDto = new AdminQnaBoardDto();
        adminQnaBoardDto.setQna_board_seq(qna_board_seq);
        AdminQnaBoardDto resultDto = adminQnaBoardService.getQnaBoardView(adminQnaBoardDto);
        List<AdminQnaBoardDto> fileList = adminQnaBoardService.getAllQnaBoardFiles(adminQnaBoardDto);
        List<AdminQnaBoardDto> qnaCmtList = adminQnaBoardService.getAllQnaCmtList(adminQnaBoardDto);

        model.addAttribute("qnaBoardView", resultDto);
        model.addAttribute("fileList", fileList);
        model.addAttribute("qnaCmtList", qnaCmtList);
        return "/qnaBoard/qnaUserView";
    }

    @PostMapping("/admin/qnaCmt/save")
    @ResponseBody
    public HashMap<String, Object> adminQnaCmtSave(@RequestBody AdminQnaBoardDto adminQnaBoardDto) {
        adminQnaBoardService.qnaCommentWrite(adminQnaBoardDto);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "success");

        return resultMap;
    }

    @PostMapping("/admin/qnaCmt/modifyOk")
    public HashMap<String, Object> adminQnaCmtModifyOk(@RequestBody AdminQnaBoardDto adminQnaBoardDto) {
        adminQnaBoardService.qnaCommentUpdate(adminQnaBoardDto);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "success");

        return resultMap;
    }

    @GetMapping("/admin/qna/list/delete/{qna_board_seq}")
    @ResponseBody
    public HashMap<String, Object> qnaBoardSoftDelete(AdminQnaBoardDto adminQnaBoardDto) {
        adminQnaBoardService.qnaBoardSoftDelete(adminQnaBoardDto);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        return  resultMap;
    }

//    @GetMapping("/admin/qnaCmt/list/delete/{qna_cmt_seq}")
//    @ResponseBody
//    public HashMap<String, Object> qnaCmtSoftDelete(AdminQnaBoardDto adminQnaBoardDto) {
//        adminQnaBoardService.qnaCmtSoftDelete(adminQnaBoardDto);
//        HashMap<String, Object> resultMap = new HashMap<>();
//        resultMap.put("result", "success");
//        return  resultMap;
//    }

    @PostMapping("/admin/qnaCmt/delete/{qna_cmt_seq}")
    @ResponseBody
    public HashMap<String, Object> adminQnaCmtDelete(@PathVariable("qna_cmt_seq") Long qnaCmtSeq) {
        adminQnaBoardService.qnaCommentDelete(qnaCmtSeq);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "success");

        return resultMap;
    }
}