package com.fcc.PureSync.dao;

import com.fcc.PureSync.dto.MenuDto;
import com.fcc.PureSync.entity.Menu;
import jakarta.annotation.Resource;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuDao {

    @Resource(name="sm")
    SqlSessionTemplate sm;

    public List<MenuDto> getMenuList( MenuDto menuTo ) {
        List<MenuDto> list = sm.selectList("getMenuAllList", menuTo );
        return  list;
    }

    public List<MenuDto> getMenuWhenTotal ( MenuDto menuTo ) {
        List<MenuDto> list = sm.selectList("getMenuTotalKcal", menuTo );
        return  list;
    }
}
