package com.fcc.PureSync.context.admin.userBoard.controller;

import com.fcc.PureSync.core.Pager;
import com.fcc.PureSync.context.admin.userBoard.dto.AdminBoardDto;
import com.fcc.PureSync.context.admin.userBoard.service.AdminBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminBoardController {

    private final AdminBoardService adminBoardService;

    @GetMapping("admin/user/list/{pg}")
    public String userBoardList(Model model, AdminBoardDto adminBoardDto , @PathVariable("pg") int pg) {
        String searchText = URLDecoder.decode( adminBoardDto.getSearchText(), StandardCharsets.UTF_8 );
            if( searchText == null ) {
                searchText = " ";
            }
        adminBoardDto.setSearchText(searchText);
        adminBoardDto.setStart(pg*10);
        List<AdminBoardDto> userBoardList = adminBoardService.getAllUserBoardList(adminBoardDto);
        int totalcnt = adminBoardService.getBoardTotalcnt(adminBoardDto);


        model.addAttribute("page", Pager.makePage(10, totalcnt , pg));
        model.addAttribute("userBoardList", userBoardList);
        model.addAttribute("total" , totalcnt );
        model.addAttribute("pg", pg );
        return "userBoard/userList";
    }

    @GetMapping("/admin/cmt/list/{pg}")
    public String adminCmtList(Model model, AdminBoardDto adminBoardDto , @PathVariable("pg") int pg) {
        String searchText = URLDecoder.decode( adminBoardDto.getSearchText(), StandardCharsets.UTF_8 );
        if( searchText == null ) {
            searchText = " ";
        }

        int totalcnt = adminBoardService.getCmtTotalcnt(adminBoardDto);

        adminBoardDto.setStart(pg*10);
        List<AdminBoardDto> userCmtList = adminBoardService.getAllUserCmtList(adminBoardDto);
        System.out.println(userCmtList);
        model.addAttribute("page", Pager.makePage(10, totalcnt, pg));
        model.addAttribute("userCmtList", userCmtList);
        model.addAttribute("total", totalcnt);
        model.addAttribute("pg", pg );

        return "userBoard/userCmtList";
    }

    @GetMapping("/admin/user/view/{board_seq}")
    public String userBoardView(Model model, @PathVariable("board_seq") long board_seq) {
        AdminBoardDto adminBoardDto = new AdminBoardDto();
        adminBoardDto.setBoard_seq(board_seq);
        AdminBoardDto resultDto = adminBoardService.getUserBoardView(adminBoardDto);
        List<AdminBoardDto> fileList = adminBoardService.getAllBoardFiles(adminBoardDto);
        model.addAttribute("userBoardView", resultDto);
        model.addAttribute("fileList", fileList);
        return "userBoard/userView";
    }

    @GetMapping("admin/user/list/delete/{board_seq}")
    @ResponseBody
    public HashMap<String, Object> userBoardSoftDelete( AdminBoardDto adminBoardDto) {
        adminBoardService.userBoardSoftDelete(adminBoardDto);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        return  resultMap;
    }

    @GetMapping("admin/cmt/list/delete/{cmt_seq}")
    @ResponseBody
    public HashMap<String, Object> cmtSoftDelete( AdminBoardDto adminBoardDto) {
        adminBoardService.cmtSoftDelete(adminBoardDto);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        return  resultMap;
    }


}
