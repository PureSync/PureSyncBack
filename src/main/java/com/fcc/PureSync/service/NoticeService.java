package com.fcc.PureSync.service;
import com.fcc.PureSync.dao.NoticeDao;
import com.fcc.PureSync.dto.NoticeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {

    private final NoticeDao noticeDao;

    public List<NoticeDto> getAllNoticeList (NoticeDto noticeDto) {
        return  noticeDao.getAllNoticeList(noticeDto);
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
