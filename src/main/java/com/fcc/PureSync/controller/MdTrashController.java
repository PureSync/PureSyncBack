package com.fcc.PureSync.controller;

import com.fcc.PureSync.dto.MdTrashRequestDto;
import com.fcc.PureSync.dto.ResultDto;
import com.fcc.PureSync.service.MdTrashService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mind/trash")
public class MdTrashController {
    private final MdTrashService mdTrashService;

    @GetMapping("/list/{memId}")
    public ResultDto getMdTrashList(@PathVariable String memId) {

        return mdTrashService.getMdTrashList(memId);
    }

    @GetMapping("{tsSeq}")
    public ResultDto getMdTrash(@PathVariable Long tsSeq) {
        return mdTrashService.getMdTrash(tsSeq);
    }

    @PostMapping
    public ResultDto writeMdTrash(@RequestBody MdTrashRequestDto dto) {
        return mdTrashService.writeMdTrash(dto);
    }

    @DeleteMapping("{tsSeq}")
    public ResultDto deleteMdTrash(@PathVariable Long tsSeq) {
        return mdTrashService.deleteMdTrash(tsSeq);
    }
}
