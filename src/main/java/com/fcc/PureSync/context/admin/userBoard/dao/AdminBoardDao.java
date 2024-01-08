package com.fcc.PureSync.context.admin.userBoard.dao;

import com.fcc.PureSync.context.admin.userBoard.dto.AdminBoardDto;
import jakarta.annotation.Resource;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminBoardDao {

    @Resource(name="sm")
    SqlSessionTemplate sm;

    public List<AdminBoardDto> getAllUserBoardList( AdminBoardDto adminBoardDto ) {
        List<AdminBoardDto> list = sm.selectList("getAllUserBoardList", adminBoardDto);
        return list;
    }

    public int getBoardTotalcnt(AdminBoardDto adminBoardDto) {
        return sm.selectOne("Board_getTotalCnt", adminBoardDto);
    }

    public int getCmtTotalcnt(AdminBoardDto adminBoardDto) {
        return sm.selectOne("Cmt_getTotalCnt", adminBoardDto);
    }

    public List<AdminBoardDto> getAllFiles( AdminBoardDto adminBoardDto ) {
        List<AdminBoardDto> list = sm.selectList("getAllFiles", adminBoardDto);
        return list;
    }

    public List<AdminBoardDto> getAllUserCmtList( AdminBoardDto adminBoardDto ) {
        List<AdminBoardDto> list = sm.selectList("getAllUserCmtList", adminBoardDto);
        return list;
    }

    public AdminBoardDto getUserBoardView( AdminBoardDto adminBoardDto ) {
        return sm.selectOne("userBoardView", adminBoardDto );
    }

    public void cmtSoftDelete ( AdminBoardDto adminBoardDto ) {
        sm.update("cmtSoftDelete", adminBoardDto );
    }

    public void userBoardSoftDelete ( AdminBoardDto adminBoardDto ) {
        sm.update("userBoardSoftDelete", adminBoardDto );
    }

}
