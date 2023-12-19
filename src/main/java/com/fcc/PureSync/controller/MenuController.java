package com.fcc.PureSync.controller;

import com.fcc.PureSync.common.HeaderInfo;
import com.fcc.PureSync.dto.MenuDto;
import com.fcc.PureSync.dto.MenuResponseDto;
import com.fcc.PureSync.dto.ResultDto;
import com.fcc.PureSync.entity.Menu;

import com.fcc.PureSync.jwt.CustomUserDetails;
import com.fcc.PureSync.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping( value = "/api/menu" )
@RequiredArgsConstructor
public class MenuController {
    // 서비스에서 예외처리
    private final MenuService menuService;

    @GetMapping("/foodList")
    public ResultDto getAllFoodList(String foodName) {
        return menuService.getAllFoods(foodName);
    }

    @GetMapping("/list")
    public ResultDto  getAllMenuList (MenuDto menuTo, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long memSeq = customUserDetails.getMemSeq();
        return menuService.getMenuAllList(menuTo, memSeq);
    }

    @PostMapping("/save")
    public ResultDto menuInsert(@RequestBody MenuResponseDto menu, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long memSeq = customUserDetails.getMemSeq();
        return menuService.insertMenu(menu, memSeq);
    }

//    @PutMapping("/update")
//    public ResultDto menuUpdate( @RequestBody Menu menu, @AuthenticationPrincipal CustomUserDetails customUserDetails ) {
//        Long memSeq = customUserDetails.getMemSeq();
//        return menuService.updateMenu(menu, memSeq);
//    }

    @PostMapping ("/delete")
    public ResultDto menuDelete( @RequestBody MenuResponseDto menu, @AuthenticationPrincipal CustomUserDetails customUserDetails ) {
        Long memSeq = customUserDetails.getMemSeq();
        return menuService.deleteMenu(menu, memSeq);
    }


}
