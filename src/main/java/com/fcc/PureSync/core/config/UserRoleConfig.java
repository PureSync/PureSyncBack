package com.fcc.PureSync.core.config;

import com.fcc.PureSync.core.exception.CustomException;
import com.fcc.PureSync.core.exception.CustomExceptionCode;

import java.util.Arrays;


public class UserRoleConfig {
    public static final String ADMIN_ID ="";
    public enum UserRole {
        DISABLED(0),REST(1), DELETE(2),BLOCK(3),USER(4), ADMIN(5) ;
        private Integer level;

        UserRole(Integer level) {
            this.level = level;
        }

        public Integer getLevel() {
            return this.level;
        }
        // 람다 표현식을 사용하여 정수를 문자열로 바꾸는 함수
        public static String convertStringFromUserRole(Integer inputLevel) {
            return Arrays.stream(UserRole.values()).
            filter(userRole -> userRole.getLevel().equals(inputLevel)).findFirst()
                    .map(Enum::name)
                    .orElseThrow(()->new CustomException(CustomExceptionCode.NOT_FOUND_USER));
        }

    }
}
