package com.fcc.PureSync.context.diary.controller;

import com.fcc.PureSync.context.diary.dto.MdDiaryRequestDto;
import com.fcc.PureSync.dto.ResultDto;
import com.fcc.PureSync.jwt.CustomUserDetails;
import com.fcc.PureSync.context.diary.service.MdDiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mind/diary")
public class MdDiaryController {
    private final MdDiaryService mdDiaryService;
    @GetMapping("/list")
    public ResultDto getMdDiaryList(@AuthenticationPrincipal CustomUserDetails customUserDetails, Pageable pageable) {
        return mdDiaryService.getMdDiaryList(customUserDetails, pageable);
    }

    @GetMapping("/{dySeq}")
    public ResultDto getMdDiary(@PathVariable("dySeq") Long dySeq) {
        return mdDiaryService.getMdDiary(dySeq);
    }

    @PostMapping
    public ResultDto writeMdDiary(@RequestBody MdDiaryRequestDto dto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return mdDiaryService.writeMdDiary(dto, customUserDetails);
    }

    @PutMapping("/{dySeq}")
    public ResultDto updateMdDiary(@PathVariable("dySeq") Long dySeq, @RequestBody MdDiaryRequestDto dto) {
        return mdDiaryService.updateMdDiary(dySeq, dto);
    }

    @DeleteMapping("/{dySeq}")
    public ResultDto deleteMdDiary(@PathVariable("dySeq") Long dySeq) {
        return mdDiaryService.deleteMdDiary(dySeq);
    }

}