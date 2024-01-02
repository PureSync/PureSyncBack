package com.fcc.PureSync.context.admin.qnaBoard.dao;

import com.fcc.PureSync.context.admin.qnaBoard.dto.AdminQnaBoardDto;
import jakarta.annotation.Resource;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminQnaBoardDao {

    @Resource(name="sm")
    SqlSessionTemplate sm;

    public List<AdminQnaBoardDto> getAllQnaBoardList(AdminQnaBoardDto adminQnaBoardDto) {
        List<AdminQnaBoardDto> list = sm.selectList("getAllQnaBoardList", adminQnaBoardDto);
        return list;
    }

    public int getQnaBoardTotalcnt(AdminQnaBoardDto adminQnaBoardDto) {
        return sm.selectOne("QnaBoard_getTotalCnt", adminQnaBoardDto);
    }

    public int getQnaCmtTotalcnt(AdminQnaBoardDto adminQnaBoardDto) {
        return sm.selectOne("QnaCmt_getTotalCnt", adminQnaBoardDto);
    }

    public List<AdminQnaBoardDto> getAllQnaFiles(AdminQnaBoardDto adminQnaBoardDto) {
        List<AdminQnaBoardDto> list = sm.selectList("getAllQnaFiles", adminQnaBoardDto);
        return list;
    }

    public List<AdminQnaBoardDto> getAllQnaCmtList(AdminQnaBoardDto adminQnaBoardDto) {
        List<AdminQnaBoardDto> list = sm.selectList("getAllQnaCmtList", adminQnaBoardDto);
        return list;
    }

    public AdminQnaBoardDto getQnaBoardView(AdminQnaBoardDto adminQnaBoardDto) {
        return sm.selectOne("qnaBoardView", adminQnaBoardDto);
    }

    public void qnaCmtSoftDelete(AdminQnaBoardDto adminQnaBoardDto) {
        sm.update("qnaCmtSoftDelete", adminQnaBoardDto);
    }

    public void qnaBoardSoftDelete(AdminQnaBoardDto adminQnaBoardDto) {
        sm.update("qnaBoardSoftDelete", adminQnaBoardDto);
    }

}
