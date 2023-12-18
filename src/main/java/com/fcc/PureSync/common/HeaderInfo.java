package com.fcc.PureSync.common;

import com.fcc.PureSync.common.constant.Constant;
import com.fcc.PureSync.entity.Member;
import com.fcc.PureSync.exception.CustomException;
import com.fcc.PureSync.exception.CustomExceptionCode;
import com.fcc.PureSync.jwt.JwtAuthenticationFilter;
import com.fcc.PureSync.jwt.JwtUtil;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class HeaderInfo {
    /* 주의 사항
    1. import org.springframework.http.HttpEntity;
       HttpEntity는 종류가 2가지 입니다.

    2. 컨트롤러에서 HttpEntity를 받아서 보내주면 끝입니다.

    3. 프론트 측에서 memSeq, memId를 보내줄 필요가 없습니다.
    
    HttpEntity랑 폼데이터랑 충돌
    */

    private final JwtUtil jwtUtil;

    public Long getMemSeqFromHeader(HttpEntity httpEntity) {
        String accessToken = splitToken(httpEntity);
        Long memSeq = null;
        if (jwtUtil.validateToken(accessToken)) {
            memSeq = jwtUtil.getMemSeq(accessToken);
        }
        return memSeq;
    }

    public String getMemIdFromHeader(HttpEntity httpEntity) {
        String accessToken = splitToken(httpEntity);
        String memId = "";
        if (jwtUtil.validateToken(accessToken))
            memId = jwtUtil.getMemId(accessToken);
        return memId;
    }

    private String splitToken(HttpEntity httpEntity) {
        if(httpEntity==null)
            throw new CustomException(CustomExceptionCode.INVALID_JWT);
        for (Map.Entry<String, List<String>> entry : httpEntity.getHeaders().entrySet()) {
            if (Constant.AUTHORIZATION_HEADER.equalsIgnoreCase(entry.getKey())) {
                List<String> headerValue = entry.getValue();
                if (!headerValue.isEmpty()) {
                    return headerValue.get(0).substring(7);
                }
                break;
            }
        }
        throw new CustomException(CustomExceptionCode.UNSUPPORTED_JWT);
    }

}
