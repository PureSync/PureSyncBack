package com.fcc.PureSync.service;

import com.fcc.PureSync.dao.ExerciseDao;
import com.fcc.PureSync.dao.MenuDao;
import com.fcc.PureSync.dao.SummaryDao;
import com.fcc.PureSync.dto.ExerciseDto;
import com.fcc.PureSync.dto.MenuDto;
import com.fcc.PureSync.common.ResultDto;
import com.fcc.PureSync.dto.SummaryDto;
import com.fcc.PureSync.entity.Member;
import com.fcc.PureSync.exception.CustomException;
import com.fcc.PureSync.exception.CustomExceptionCode;
import com.fcc.PureSync.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SummaryService {

    private final SummaryDao summaryDao;
    private final ExerciseDao exerciseDao;
    private final MenuDao menuDao;
    private final MemberRepository memberRepository;

    public ResultDto getBodyBase(HashMap<String,String> map, Long memSeq) {

        SummaryDto summaryDto = new SummaryDto();
        ExerciseDto exerciseDto = new ExerciseDto();
        MenuDto menuDto = new MenuDto();

        Member member = memberRepository.findByMemSeq(memSeq)
                .orElseThrow(() -> new CustomException(CustomExceptionCode.NOT_FOUND_USER));

//        summaryDto.setMem_seq(Long.parseLong(map.get("mem_seq")));
//        exerciseDto.setMem_seq(Long.parseLong(map.get("mem_seq")) );

        summaryDto.setMem_seq( member.getMemSeq() );
        exerciseDto.setMem_seq( member.getMemSeq() );

        exerciseDto.setEl_date( map.get("el_date")) ;
        menuDto.setMem_seq( member.getMemSeq()  );
        menuDto.setMenu_date( map.get("menu_date"));

        List<SummaryDto> getBodyBase= summaryDao.getBodyBase(summaryDto);
        List<ExerciseDto> exerciseTotalList = exerciseDao.getExerciseTotal(exerciseDto);
        List<MenuDto> menuTotalWhenList= menuDao.getMenuWhenTotal(menuDto);
        try {
            HashMap<String, Object> data = new HashMap<String, Object>();

            data.put("getBodyBase", getBodyBase);
            data.put("exerciseTotalList", exerciseTotalList);
            data.put("menuTotalWhenList", menuTotalWhenList);

            ResultDto resultDto =  ResultDto.builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .data(data)
                    .build();
            return resultDto;
        } catch (CustomException e) {
            throw new CustomException(CustomExceptionCode.NOT_FOUND_MENU);  // 권한X
        }
    }


}