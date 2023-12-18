package com.fcc.PureSync.controller;

import com.fcc.PureSync.dto.MdDiaryRequestDto;
import com.fcc.PureSync.dto.ResultDto;
import com.fcc.PureSync.dto.SentimentRequestDto;
import com.fcc.PureSync.service.MdDiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mind/diary")
public class MdDiaryController {
    private final MdDiaryService mdDiaryService;
    @GetMapping("/list/{memId}")
    public ResultDto getMdDiaryList(@PathVariable("memId") String memId, Pageable pageable) {
        return mdDiaryService.getMdDiaryList(memId, pageable);
    }

    @GetMapping("{dySeq}")
    public ResultDto getMdDiary(@PathVariable("dySeq") Long dySeq) {
        return mdDiaryService.getMdDiary(dySeq);
    }

    @PostMapping
    public ResultDto writeMdDiary(@RequestBody MdDiaryRequestDto dto) {
        return mdDiaryService.writeMdDiary(dto);
    }

    @PutMapping("/{dySeq}")
    public ResultDto updateMdDiary(@PathVariable("dySeq") Long dySeq, @RequestBody MdDiaryRequestDto dto) {
        return mdDiaryService.updateMdDiray(dySeq, dto);
    }

    @DeleteMapping("{dySeq}")
    public ResultDto deleteMdDiary(@PathVariable("dySeq") Long dySeq) {
        return mdDiaryService.deleteMdDiary(dySeq);
    }

}