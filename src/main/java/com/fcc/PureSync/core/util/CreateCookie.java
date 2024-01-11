package com.fcc.PureSync.core.util;

import com.fcc.PureSync.core.constant.MemberConstant;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;

import java.util.Optional;

public class CreateCookie {
    @Value("${jwt.token-validity-in-seconds2}")
    private static long refreshTokenValidityInSeconds;

    public static Optional<Cookie> getCookie(HttpServletRequest httpServletRequest, String name){
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null && cookies.length> 0 ){
            for(Cookie  cookie: cookies){
                if(name.equals(cookie.getName())){
                    return Optional.of(cookie);
                }
            }
        }
        return Optional.empty();
    }

    public static void addCookie(String refreshToken, HttpServletResponse httpServletResponse){
        ResponseCookie cookie = ResponseCookie.from(MemberConstant.REFRESH_TOKEN, refreshToken)
                .path("/")
                .sameSite("Strict")
                .httpOnly(true)
                .secure(true)
                .maxAge(refreshTokenValidityInSeconds*1000)
                .build();
        httpServletResponse.addHeader("Set-Cookie",cookie.toString());
    }

}
