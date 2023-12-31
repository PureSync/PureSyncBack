package com.fcc.PureSync.common.constant;

public final class EmailConstant {
    public static final Integer EMAIL_VERIFICATION_CODE_LENGTH = 6;
    public static final Integer EMAIL_VERIFICATION_LIMIT_SECONDS = 300;

    public static final String EMAIL_TITLE = "회원 링크 인증";

    //아래 위치 변경 필요
    public static final String LOCAL_DOMAIN="http://localhost:8080"; // 백엔드 // 컨트롤러
    public static final String VERIFY_DOMAIN="http://localhost:4000"; //프론트
//    public static final String AWS_DOMAIN="http://13.239.63.115:8080";
    public static final String AWS_DOMAIN="http://fcc.puresync.site:8080";

    public static final Integer MEMBER_ENABLED_LEVEL=4;
    public static final Integer MEMBER_DISABLED_LEVEL=0;
}
