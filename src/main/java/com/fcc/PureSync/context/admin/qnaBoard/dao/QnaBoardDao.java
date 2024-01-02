package com.fcc.PureSync.context.admin.qnaBoard.dao;

import com.fcc.PureSync.context.admin.qnaBoard.dto.QnaBoardDto;
import jakarta.annotation.Resource;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QnaBoardDao {

    @Resource(name="sm")
    SqlSessionTemplate sm;

    public List<QnaBoardDto> getAllQnaBoardList(QnaBoardDto qnaBoardDto) {
        List<QnaBoardDto> list = sm.selectList("getAllQnaBoardList", qnaBoardDto);
        return list;
    }

    public int getQnaBoardTotalcnt(QnaBoardDto qnaBoardDto) {
        return sm.selectOne("QnaBoard_getTotalCnt", qnaBoardDto);
    }

    public int getQnaCmtTotalcnt(QnaBoardDto qnaBoardDto) {
        return sm.selectOne("QnaCmt_getTotalCnt", qnaBoardDto);
    }

    public List<QnaBoardDto> getAllQnaFiles(QnaBoardDto qnaBoardDto) {
        List<QnaBoardDto> list = sm.selectList("getAllQnaFiles", qnaBoardDto);
        return list;
    }

    public List<QnaBoardDto> getAllQnaCmtList(QnaBoardDto qnaBoardDto) {
        List<QnaBoardDto> list = sm.selectList("getAllQnaCmtList", qnaBoardDto);
        return list;
    }

    public QnaBoardDto getQnaBoardView(QnaBoardDto qnaBoardDto) {
        return sm.selectOne("qnaBoardView", qnaBoardDto);
    }

    public void qnaCmtSoftDelete(QnaBoardDto qnaBoardDto) {
        sm.update("qnaCmtSoftDelete", qnaBoardDto);
    }

    public void qnaBoardSoftDelete(QnaBoardDto qnaBoardDto) {
        sm.update("qnaBoardSoftDelete", qnaBoardDto);
    }

}
