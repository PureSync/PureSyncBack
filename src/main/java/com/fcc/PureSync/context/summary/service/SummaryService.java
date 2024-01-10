package com.fcc.PureSync.context.summary.service;

import com.fcc.PureSync.context.exercise.dao.ExerciseDao;
import com.fcc.PureSync.context.menu.dao.MenuDao;
import com.fcc.PureSync.context.summary.dao.SummaryDao;
import com.fcc.PureSync.context.exercise.dto.ExerciseDto;
import com.fcc.PureSync.context.menu.dto.MenuDto;
import com.fcc.PureSync.core.ResultDto;
import com.fcc.PureSync.context.summary.dto.SummaryDto;
import com.fcc.PureSync.context.member.entity.Member;
import com.fcc.PureSync.core.exception.CustomException;
import com.fcc.PureSync.core.exception.CustomExceptionCode;
import com.fcc.PureSync.context.member.repository.MemberRepository;
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

            ResultDto resultDto =  ResultDto.of(HttpStatus.OK.value(), HttpStatus.OK, "성공", data);
            return resultDto;
        } catch (CustomException e) {
            throw new CustomException(CustomExceptionCode.NOT_FOUND_MENU);  // 권한X
        }
    }


}