package com.fcc.PureSync.dao;

import com.fcc.PureSync.dto.AdminQnaBoardDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public void qnaCommentWrite( AdminQnaBoardDto adminQnaBoardDto ) {
        sm.insert("qnaCommentWrite", adminQnaBoardDto);
    }

    public void qnaCommentUpdate( AdminQnaBoardDto adminQnaBoardDto ) {
        sm.update("qnaCommentUpdate", adminQnaBoardDto);
    }

    public void qnaCommentDelete( AdminQnaBoardDto adminQnaBoardDto ) {
        sm.delete("qnaCommentDelete", adminQnaBoardDto);
    }
}