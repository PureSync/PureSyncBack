package com.fcc.PureSync.service;

import com.fcc.PureSync.dao.ExerciseDao;
import com.fcc.PureSync.dao.MenuDao;
import com.fcc.PureSync.dao.SummaryDao;
import com.fcc.PureSync.dto.ExerciseDto;
import com.fcc.PureSync.dto.MenuDto;
import com.fcc.PureSync.dto.ResultDto;
import com.fcc.PureSync.dto.SummaryDto;
import com.fcc.PureSync.entity.Food;
import com.fcc.PureSync.entity.Menu;
import com.fcc.PureSync.exception.CustomException;
import com.fcc.PureSync.exception.CustomExceptionCode;
import com.fcc.PureSync.repository.FoodRepository;
import com.fcc.PureSync.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SummaryService {

    private final SummaryDao summaryDao;
    private final ExerciseDao exerciseDao;
    private final MenuDao menuDao;

    public ResultDto getBodyBase(HashMap<String,String> map) {
        System.out.println("map >>>>>>>>> " + (map.get("mem_seq") ) );

        SummaryDto summaryDto = new SummaryDto();
        ExerciseDto exerciseDto = new ExerciseDto();
        MenuDto menuDto = new MenuDto();

        summaryDto.setMem_seq(Long.parseLong(map.get("mem_seq")));
        exerciseDto.setMem_seq(Long.parseLong(map.get("mem_seq")) );
        exerciseDto.setEl_date( map.get("el_date")) ;
        menuDto.setMem_seq( Long.parseLong(map.get("mem_seq")) );
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