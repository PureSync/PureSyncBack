package com.fcc.PureSync.context.admin.qnaBoard.service;

import com.fcc.PureSync.context.admin.qnaBoard.dao.QnaCommentDao;
import com.fcc.PureSync.context.admin.qnaBoard.dao.QnaBoardDao;
import com.fcc.PureSync.context.admin.qnaBoard.dto.QnaBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class QnaBoardService {

    private final QnaBoardDao qnaBoardDao;
    private final QnaCommentDao qnaCommentDao;

    // 유저 게시판 리스트
    public List<QnaBoardDto> getAllQnaBoardList (QnaBoardDto qnaBoardDto) {
        return  qnaBoardDao.getAllQnaBoardList(qnaBoardDto);
    }

    public int getQnaBoardTotalcnt(QnaBoardDto qnaBoardDto) {
        return qnaBoardDao.getQnaBoardTotalcnt(qnaBoardDto);
    }

//    public int getQnaCmtTotalcnt(AdminQnaBoardDto adminQnaBoardDto) {
//        return adminQnaBoardDao.getQnaCmtTotalcnt(adminQnaBoardDto);
//    }

    public List<QnaBoardDto> getAllQnaBoardFiles (QnaBoardDto qnaBoardDto) {
        return  qnaBoardDao.getAllQnaFiles(qnaBoardDto);
    }

    // 유저 댓글 리스트
    public List<QnaBoardDto> getAllQnaCmtList (QnaBoardDto qnaBoardDto) {
        return  qnaBoardDao.getAllQnaCmtList(qnaBoardDto);
    }

    // 유저 게시판 뷰
    public QnaBoardDto getQnaBoardView (QnaBoardDto qnaBoardDto) {
        return  qnaBoardDao.getQnaBoardView(qnaBoardDto);
    }

//    // 댓글 관리자 권한으로 삭제
//    public void qnaCmtSoftDelete ( AdminQnaBoardDto adminQnaBoardDto ) {
//        adminQnaBoardDao.qnaCmtSoftDelete(adminQnaBoardDto);
//    }

    // 게시글 관리자 권한으로 삭제
    public void qnaBoardSoftDelete ( QnaBoardDto qnaBoardDto) {
        qnaBoardDao.qnaBoardSoftDelete(qnaBoardDto);
    }

    public void qnaCommentWrite(QnaBoardDto qnaBoardDto) {
        qnaCommentDao.qnaCommentWrite(qnaBoardDto);
    }

    public void qnaCommentUpdate(QnaBoardDto qnaBoardDto) {
        qnaCommentDao.qnaCommentUpdate(qnaBoardDto);
    }

    public void qnaCommentDelete(Long qnaCmtSeq) {
        qnaCommentDao.qnaCommentDelete(qnaCmtSeq);
    }
}
