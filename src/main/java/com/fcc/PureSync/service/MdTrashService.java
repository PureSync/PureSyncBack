package com.fcc.PureSync.service;

import com.fcc.PureSync.dto.MdDiaryRequestDto;
import com.fcc.PureSync.dto.MdTrashRequestDto;
import com.fcc.PureSync.dto.MdTrashResponseDto;
import com.fcc.PureSync.dto.ResultDto;
import com.fcc.PureSync.entity.MdDiary;
import com.fcc.PureSync.entity.MdTrash;
import com.fcc.PureSync.entity.Member;
import com.fcc.PureSync.exception.CustomException;
import com.fcc.PureSync.exception.CustomExceptionCode;
import com.fcc.PureSync.repository.MdTrashRepository;
import com.fcc.PureSync.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MdTrashService {
    private final MdTrashRepository mdTrashRepository;
    private final MemberRepository memberRepository;

    public ResultDto getMdTrashList(String memId) {

        Member member = memberRepository.findByMemId(memId).orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));
        List<MdTrash> mdTrashList =  mdTrashRepository.findAllByMemberAndTsStatusOrderByTsWdateDesc(member, true);
        List<MdTrashResponseDto> mdTrashResponseDtoList = mdTrashList.stream().map(e -> entityToDto(e)).toList();
        int count = mdTrashList.size();
        HashMap<String, Object> data = new HashMap<>();
        data.put("mdTrashList", mdTrashResponseDtoList);
        data.put("count", count);

        return buildResultDto(200, HttpStatus.OK, "success", data);
    }

    public ResultDto getMdTrash(Long tsSeq) {
        MdTrash mdTrash = mdTrashRepository.findById(tsSeq).orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_ARTICLE));
        MdTrashResponseDto mdTrashResponseDto = entityToDto(mdTrash);
        HashMap<String, Object> data = new HashMap<>();
        data.put("mdTrash", mdTrashResponseDto);

        return buildResultDto(200, HttpStatus.OK, "success", data);
    }

    public ResultDto writeMdTrash(MdTrashRequestDto dto) {
        MdTrash mdTrash = dtoToEntity(dto);
        mdTrashRepository.save(mdTrash);
        HashMap<String, Object> data = new HashMap<>();
        data.put("mdTrash", mdTrash);

        ResultDto resultDto = buildResultDto(201, HttpStatus.CREATED, "insert Complete", data);

        return resultDto;
    }

    public ResultDto deleteMdTrash(Long tsSeq) {
        MdTrash mdTrash = mdTrashRepository.findById(tsSeq).orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_ARTICLE));
        MdTrash deletedMdTrash =
                MdTrash.builder()
                        .tsSeq(mdTrash.getTsSeq())
                        .tsContents(mdTrash.getTsContents())
                        .tsWdate(mdTrash.getTsWdate())
                        .tsStatus(false)
                        .member(mdTrash.getMember())
                        .build();

        mdTrashRepository.save(deletedMdTrash);
        HashMap<String, Object> data = new HashMap<>();
        data.put("mdTrash", deletedMdTrash);

        ResultDto resultDto = buildResultDto(200, HttpStatus.OK, "delete Complete", data);

        return resultDto;
    }

    public void deleteYesterdayMdTrashes(){
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        LocalDateTime yesterdayEnd = LocalDateTime.of(yesterday.getYear(), yesterday.getMonth(), yesterday.getDayOfMonth(), 23,59,59);
        List<MdTrash> yesterdayMdTrashlist = mdTrashRepository.findAllByTsStatusAndTsWdateBefore(true, yesterdayEnd);

        yesterdayMdTrashlist.stream().forEach(e -> {
            MdTrash deletedMdTrash = MdTrash.builder()
                    .tsSeq(e.getTsSeq())
                    .tsContents(e.getTsContents())
                    .tsWdate(e.getTsWdate())
                    .tsStatus(false)
                    .member(e.getMember())
                    .build();

            mdTrashRepository.save(deletedMdTrash);
        });

    }

    private MdTrashResponseDto entityToDto(MdTrash mdTrash) {
        return MdTrashResponseDto.builder()
                .tsSeq(mdTrash.getTsSeq())
                .tsContents(mdTrash.getTsContents())
                .build();
    }

    private MdTrash dtoToEntity(MdTrashRequestDto dto) {
        Member dtoMember = memberRepository.findByMemId(dto.getMemId()).orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));
        return MdTrash.builder()
                .tsContents(dto.getTsContents())
                .tsStatus(true)
                .member(dtoMember)
                .build();
    }

    public ResultDto buildResultDto(int code, HttpStatus httpStatuss, String message, HashMap<String, Object> data) {
        return ResultDto.builder()
                .code(code)
                .httpStatus(httpStatuss)
                .message(message)
                .data(data)
                .build();
    }



}
