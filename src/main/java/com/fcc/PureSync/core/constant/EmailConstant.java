package com.fcc.PureSync.core.constant;

public final class EmailConstant {

    //SignUp
    public static final Integer EMAIL_VERIFICATION_CODE_LENGTH = 6;
    public static final Integer EMAIL_VERIFICATION_LIMIT_SECONDS = 300;
    public static final String EMAIL_VERIFICATION_LINK = "%s/api/mail/verify?verificationCode=%s&email=%s";
    public static final String EMAIL_TITLE = "회원 링크 인증";

    //Verify
    public static final String SUCCESS_VERIFICATION_CODE = "회원 링크 인증 성공";
    public static final Integer MEMBER_ENABLED_LEVEL=4;

    //TemporaryPassword
    public static final String TEMPORARY_PASSWORD = "임시비밀번호";

    //아래 위치 변경 필요
    public static final String LOCAL_DOMAIN="http://localhost:8080"; // 백엔드 // 컨트롤러
    public static final String VERIFY_DOMAIN="http://localhost:4000"; //프론트
//    public static final String AWS_DOMAIN="http://13.239.63.115:8080";
    public static final String AWS_DOMAIN="http://fcc.puresync.site:8080";
    public static final String AWS_REVERSE_PROXY_DOMAIN="http://fcc.puresync.site";


}
