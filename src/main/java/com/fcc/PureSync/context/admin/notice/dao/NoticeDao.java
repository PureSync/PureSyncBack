package com.fcc.PureSync.context.admin.notice.dao;

import com.fcc.PureSync.context.admin.notice.dto.NoticeDto;
import jakarta.annotation.Resource;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoticeDao {

    @Resource(name="sm")
    SqlSessionTemplate sm;

    public List<NoticeDto> getAllNoticeList( NoticeDto noticeDto ) {
        List<NoticeDto> list = sm.selectList("getAllNoticeList", noticeDto);
        return list;
    }

    public List<NoticeDto> getNoticeListTopThree( NoticeDto noticeDto ) {
        List<NoticeDto> list = sm.selectList("getNoticeBoardTopThree", noticeDto);
        return list;
    }

    public int getNoticeTotalcnt(NoticeDto noticeDto) {
        return sm.selectOne("Notice_getTotalCnt", noticeDto);
    }

    public NoticeDto noticeBoardView( NoticeDto noticeDto ) {
        return sm.selectOne("noticeBoardView", noticeDto );
    }

    public void noticeBoardWrite( NoticeDto noticeDto ) {
        sm.insert("noticeBoardWrite", noticeDto);
    }

    public void noticeBoardUpdate( NoticeDto noticeDto ) {
        sm.update("noticeBoardUpdate", noticeDto);
    }

    public void noticeBoardDelete( NoticeDto noticeDto ) {
        sm.delete("noticeBoardDelete", noticeDto);
    }



}
