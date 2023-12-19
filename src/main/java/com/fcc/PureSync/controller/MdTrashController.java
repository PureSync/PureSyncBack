package com.fcc.PureSync.controller;

import com.fcc.PureSync.dto.MdTrashRequestDto;
import com.fcc.PureSync.dto.ResultDto;
import com.fcc.PureSync.jwt.CustomUserDetails;
import com.fcc.PureSync.service.MdTrashService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mind/trash")
public class MdTrashController {
    private final MdTrashService mdTrashService;

    @GetMapping("/list")
    public ResultDto getMdTrashList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        return mdTrashService.getMdTrashList(customUserDetails);
    }

    @GetMapping("{tsSeq}")
    public ResultDto getMdTrash(@PathVariable Long tsSeq) {
        return mdTrashService.getMdTrash(tsSeq);
    }

    @PostMapping
    public ResultDto writeMdTrash(@RequestBody MdTrashRequestDto dto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return mdTrashService.writeMdTrash(dto, customUserDetails);
    }

    @DeleteMapping("{tsSeq}")
    public ResultDto deleteMdTrash(@PathVariable Long tsSeq) {
        return mdTrashService.deleteMdTrash(tsSeq);
    }
}
