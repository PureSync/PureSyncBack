package com.fcc.PureSync.core.constant;

public final class MemberConstant {
    //Token
    public static final String AUTHORIZATION_HEADER="Authorization";
    public static final String ACCESS_TOKEN="access_token";

    public static final String REFRESH_TOKEN="refresh_token";

    //SignUp
    public static final String SUCCESS_SIGNUP="회원가입 성공했습니다.";
    public static final String MEMBER_ID="memId.";
    public static final String MEMBER_NICKNAME="memNick";
    public static final String MEMBER_EMAIL="memEmail";
    public static final String SUCCESS_DUPLICATE_ID="memId.";
    public static final String SUCCESS_DUPLICATE_NICKNAME="memNick";
    public static final String SUCCESS_DUPLICATE_EMAIL="memEmail";
    public static final Integer MAX_PASSWORD_LENGTH = 14;

    //Login
    public static final String SUCCESS_LOGIN="로그인 성공했습니다.";

    //SearchPassword
    public static final  String SUCCESS_TEMPORARYPASSWORD_SEND_MAIL = "임시 비밀번호 전송 성공했습니다.";

    //SearchID
    public static final  String SUCCESS_SEARCH_ID = "아이디 찾기에 성공했습니다.";
}
