package com.fcc.PureSync.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;

import java.util.Optional;

public class CreateCookie {

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

//    public static void addCookie(HttpServletResponse httpServletResponse, String name, String value, int maxAge){
//        ResponseCookie cookie = ResponseCookie.from(name, value)
//                .path("/")
//                .sameSite("None") //배포시 변경 필요
//                .httpOnly(false) //배포시 변경 필요
//                .secure(false) //배포시 변경 필요
//                .maxAge(maxAge)
//                .build();
//        httpServletResponse.addHeader("Set-Cookie",cookie.toString());
//    }

}
