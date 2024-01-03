package com.fcc.PureSync.service;
import com.fcc.PureSync.dao.NoticeDao;
import com.fcc.PureSync.dto.NoticeDto;
import com.fcc.PureSync.common.ResultDto;
import com.fcc.PureSync.exception.CustomException;
import com.fcc.PureSync.exception.CustomExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {

    private final NoticeDao noticeDao;

    public List<NoticeDto> getAllNoticeList (NoticeDto noticeDto) {
        return  noticeDao.getAllNoticeList(noticeDto);
    }

    public ResultDto getNoticeListTopThree( NoticeDto noticeDto ) {
        List<NoticeDto> noticeList = noticeDao.getNoticeListTopThree(noticeDto);
        try {
            HashMap<String, Object> data = new HashMap<>();
            data.put("noticeList", noticeList );
            ResultDto resultDto =  ResultDto.builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .data(data)
                    .build();
            return resultDto;
        } catch (CustomException e) {
            throw new CustomException(CustomExceptionCode.NOT_FOUND_NOTICELIST);  // 권한X
        }
    }

    public ResultDto detailNotice( NoticeDto noticeDto ) {
        NoticeDto noticeViewDto = noticeDao.noticeBoardView(noticeDto);
        try {
            HashMap<String, Object> data = new HashMap<>();
            data.put("noticeViewDto", noticeViewDto );
            ResultDto resultDto =  ResultDto.builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .data(data)
                    .build();
            return resultDto;
        } catch (CustomException e) {
            throw new CustomException(CustomExceptionCode.NOT_FOUND_NOTICELIST);  // 권한X
        }
    }


    public int getNoticeTotalcnt(NoticeDto noticeDto) {
        return noticeDao.getNoticeTotalcnt(noticeDto);
    }

    public NoticeDto noticeBoardView(NoticeDto noticeDto ) {
        return  noticeDao.noticeBoardView(noticeDto);
    }

    public void noticeBoardWrite(NoticeDto noticeDto) {
        noticeDao.noticeBoardWrite(noticeDto);
    }

    public void noticeBoardUpdate(NoticeDto noticeDto) {
        noticeDao.noticeBoardUpdate(noticeDto);
    }

    public void noticeBoardDelete(NoticeDto noticeDto) {
        noticeDao.noticeBoardDelete(noticeDto);
    }



}
