package com.fcc.PureSync.context.admin.qnaBoard.dao;

import com.fcc.PureSync.context.admin.qnaBoard.dto.QnaBoardDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QnaCommentDao {
    @Autowired
    SqlSessionTemplate sm;

//    public List<AdminQnaBoardDto> getAllQnaCmtList(AdminQnaBoardDto adminQnaBoardDto ) {
//        List<AdminQnaBoardDto> list = sm.selectList("getAllQnaCmtList", adminQnaBoardDto);
//        return list;
//    }
//
//    public int getQnaCmtTotalcnt(AdminQnaBoardDto adminQnaBoardDto) {
//        return sm.selectOne("QnaCmt_getTotalCnt", adminQnaBoardDto);
//    }
//
//    public AdminQnaBoardDto qnaCommentView( AdminQnaBoardDto adminQnaBoardDto ) {
//        return sm.selectOne("qnaCommentView", adminQnaBoardDto );
//    }

    public void qnaCommentWrite(QnaBoardDto qnaBoardDto) {
        sm.insert("qnaCommentWrite", qnaBoardDto);
    }

    public void qnaCommentUpdate(QnaBoardDto qnaBoardDto) {
        sm.update("qnaCommentUpdate", qnaBoardDto);
    }

    public void qnaCommentDelete(Long qnaCmtSeq) {
        sm.delete("qnaCommentDelete", qnaCmtSeq);
    }
}