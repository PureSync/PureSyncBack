package com.fcc.PureSync.context.trash.service;

import com.fcc.PureSync.context.trash.dto.MdTrashRequestDto;
import com.fcc.PureSync.context.trash.dto.MdTrashResponseDto;
import com.fcc.PureSync.core.ResultDto;
import com.fcc.PureSync.context.trash.entity.MdTrash;
import com.fcc.PureSync.context.member.entity.Member;
import com.fcc.PureSync.core.exception.CustomException;
import com.fcc.PureSync.core.exception.CustomExceptionCode;
import com.fcc.PureSync.core.jwt.CustomUserDetails;
import com.fcc.PureSync.context.trash.repository.MdTrashRepository;
import com.fcc.PureSync.context.member.repository.MemberRepository;
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

    public ResultDto getMdTrashList(CustomUserDetails customUserDetails) {

        Member member = memberRepository.findById(customUserDetails.getMemSeq()).orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));
        List<MdTrash> mdTrashList =  mdTrashRepository.findAllByMemberAndTsStatusOrderByTsWdateDesc(member, true);
        List<MdTrashResponseDto> mdTrashResponseDtoList = mdTrashList.stream().map(e -> entityToDto(e)).toList();
        int count = mdTrashList.size();
        HashMap<String, Object> data = new HashMap<>();
        data.put("mdTrashList", mdTrashResponseDtoList);
        data.put("count", count);

        return ResultDto.of(HttpStatus.OK.value(), HttpStatus.OK, "success", data);
    }

    public ResultDto getMdTrash(Long tsSeq) {
        MdTrash mdTrash = mdTrashRepository.findById(tsSeq).orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_ARTICLE));
        MdTrashResponseDto mdTrashResponseDto = entityToDto(mdTrash);
        HashMap<String, Object> data = new HashMap<>();
        data.put("mdTrash", mdTrashResponseDto);

        return ResultDto.of(HttpStatus.OK.value(), HttpStatus.OK, "success", data);
    }

    public ResultDto writeMdTrash(MdTrashRequestDto dto, CustomUserDetails customUserDetails) {
        MdTrash mdTrash = dtoToEntity(dto, customUserDetails.getMemSeq());
        mdTrashRepository.save(mdTrash);
        HashMap<String, Object> data = new HashMap<>();
        data.put("mdTrash", entityToDto(mdTrash));
        data.put("memId", customUserDetails.getUsername());

        return ResultDto.of(HttpStatus.CREATED.value(), HttpStatus.CREATED, "insert Complete", data);
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
        data.put("mdTrash", entityToDto(deletedMdTrash));

        return ResultDto.of(HttpStatus.OK.value(), HttpStatus.OK, "delete Complete", data);
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

    private MdTrash dtoToEntity(MdTrashRequestDto dto, Long memSeq) {
        Member dtoMember = memberRepository.findById(memSeq).orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));
        return MdTrash.builder()
                .tsContents(dto.getTsContents())
                .tsStatus(true)
                .member(dtoMember)
                .build();
    }
}
