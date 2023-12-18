package com.fcc.PureSync.config;

public class UserRoleConfig {
    public static final String ADMIN_ID ="";
    public enum UserRole {
        DISABLED(0),REST(1), DELETE(2),BLOCK(3),USER(4), ADMIN(5);
        private Integer level;

        UserRole(Integer level) {
            this.level = level;
        }

        public Integer getLevel() {
            return this.level;
        }

    }
}
