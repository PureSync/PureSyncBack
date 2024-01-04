package com.fcc.PureSync.context.admin.userBoard.service;
import com.fcc.PureSync.context.admin.userBoard.dao.AdminBoardDao;
import com.fcc.PureSync.context.admin.userBoard.dto.AdminBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminBoardService {

    private final AdminBoardDao adminBoardDao;

    // 유저 게시판 리스트
    public List<AdminBoardDto> getAllUserBoardList (AdminBoardDto adminBoardDto) {
        return  adminBoardDao.getAllUserBoardList(adminBoardDto);
    }

    public int getBoardTotalcnt(AdminBoardDto adminBoardDto) {
        return adminBoardDao.getBoardTotalcnt(adminBoardDto);
    }

    public int getCmtTotalcnt(AdminBoardDto adminBoardDto) {
        return adminBoardDao.getCmtTotalcnt(adminBoardDto);
    }

    public List<AdminBoardDto> getAllBoardFiles (AdminBoardDto adminBoardDto) {
        return  adminBoardDao.getAllFiles( adminBoardDto );
    }

    // 유저 댓글 리스트
    public List<AdminBoardDto> getAllUserCmtList (AdminBoardDto adminBoardDto) {
        return  adminBoardDao.getAllUserCmtList(adminBoardDto);
    }

    // 유저 게시판 뷰
    public AdminBoardDto getUserBoardView (AdminBoardDto adminBoardDto) {
        System.out.println();
        return  adminBoardDao.getUserBoardView(adminBoardDto);
    }

    // 댓글 관리자 권한으로 삭제
    public void cmtSoftDelete ( AdminBoardDto adminBoardDto ) {
        adminBoardDao.cmtSoftDelete(adminBoardDto);
    }

    // 게시글 관리자 권한으로 삭제
    public void userBoardSoftDelete ( AdminBoardDto adminBoardDto ) {
        adminBoardDao.userBoardSoftDelete(adminBoardDto);
    }



}
