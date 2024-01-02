package com.fcc.PureSync.context.admin.qnaBoard.controller;

import com.fcc.PureSync.common.Pager;
import com.fcc.PureSync.context.admin.qnaBoard.dto.QnaBoardDto;
import com.fcc.PureSync.context.admin.qnaBoard.service.QnaBoardService;
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
public class QnaBoardController {

    private final QnaBoardService qnaBoardService;

    @GetMapping("/admin/qna/list/{pg}")
    public String qnaBoardList(Model model, QnaBoardDto qnaBoardDto, @PathVariable("pg") int pg)  {
        String searchText = URLDecoder.decode(qnaBoardDto.getSearchText(), StandardCharsets.UTF_8);
        if( searchText == null ) {
            searchText = " ";
        }
        qnaBoardDto.setSearchText(searchText);
        qnaBoardDto.setStart(pg * 10);
        List<QnaBoardDto> qnaBoardList = qnaBoardService.getAllQnaBoardList(qnaBoardDto);

        model.addAttribute("page", Pager.makePage(10, qnaBoardService.getQnaBoardTotalcnt(qnaBoardDto), pg));
        model.addAttribute("qnaBoardList", qnaBoardList);
        model.addAttribute("pg", pg );
        return "qnaBoard/qnaUserList";
    }


    @GetMapping("/admin/qna/view/{qna_board_seq}")
    public String qnaBoardView(Model model, @PathVariable("qna_board_seq") long qna_board_seq) {
        QnaBoardDto qnaBoardDto = new QnaBoardDto();
        qnaBoardDto.setQna_board_seq(qna_board_seq);
        QnaBoardDto resultDto = qnaBoardService.getQnaBoardView(qnaBoardDto);
        List<QnaBoardDto> fileList = qnaBoardService.getAllQnaBoardFiles(qnaBoardDto);
        List<QnaBoardDto> qnaCmtList = qnaBoardService.getAllQnaCmtList(qnaBoardDto);

        model.addAttribute("qnaBoardView", resultDto);
        model.addAttribute("fileList", fileList);
        model.addAttribute("qnaCmtList", qnaCmtList);
        return "qnaBoard/qnaUserView";
    }

    @PostMapping("/admin/qnaCmt/save")
    @ResponseBody
    public HashMap<String, Object> adminQnaCmtSave(@RequestBody QnaBoardDto qnaBoardDto) {
        qnaBoardService.qnaCommentWrite(qnaBoardDto);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "success");

        return resultMap;
    }

    @PostMapping("/admin/qnaCmt/modifyOk")
    public HashMap<String, Object> adminQnaCmtModifyOk(@RequestBody QnaBoardDto qnaBoardDto) {
        qnaBoardService.qnaCommentUpdate(qnaBoardDto);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "success");

        return resultMap;
    }

    @GetMapping("/admin/qna/list/delete/{qna_board_seq}")
    @ResponseBody
    public HashMap<String, Object> qnaBoardSoftDelete(QnaBoardDto qnaBoardDto) {
        qnaBoardService.qnaBoardSoftDelete(qnaBoardDto);
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
        qnaBoardService.qnaCommentDelete(qnaCmtSeq);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "success");

        return resultMap;
    }
}