package com.fcc.PureSync.core.util;

import com.fcc.PureSync.core.config.UserRoleConfig;
import com.fcc.PureSync.core.exception.CustomException;
import com.fcc.PureSync.core.exception.CustomExceptionCode;

public class CheckUserRight {

    public static boolean checkLogin(Integer userLevel){
        boolean checkLogin = false;
        if(userLevel == UserRoleConfig.UserRole.DISABLED.getLevel())
            throw new CustomException(CustomExceptionCode.CANNOT_ACCESS_DISABLED_USER);
        if(userLevel == UserRoleConfig.UserRole.REST.getLevel())
            throw new CustomException(CustomExceptionCode.CANNOT_ACCESS_REST_USER);
        if(userLevel == UserRoleConfig.UserRole.DELETE.getLevel())
            throw new CustomException(CustomExceptionCode.CANNOT_ACCESS_DELETE_USER);
        if(userLevel == UserRoleConfig.UserRole.BLOCK.getLevel())
            throw new CustomException(CustomExceptionCode.CANNOT_ACCESS_BLOCK_USER);
        if(userLevel == UserRoleConfig.UserRole.USER.getLevel()||userLevel == UserRoleConfig.UserRole.ADMIN.getLevel())
            checkLogin=true;
        return checkLogin;
    }
}
