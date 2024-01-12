package com.fcc.PureSync.core.jwt;

import com.fcc.PureSync.core.constant.Constant;
import com.fcc.PureSync.core.exception.CustomException;
import com.fcc.PureSync.core.exception.CustomExceptionCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class SplitToken {


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
        if (httpEntity == null)
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

    public Long getMemSeqFromFromHttpServletRequest(HttpServletRequest httpServletRequest) {
        String accessToken = splitTokenFromHttpServletRequest(httpServletRequest);
        Long memSeq = null;
        if (jwtUtil.validateToken(accessToken)) {
            memSeq = jwtUtil.getMemSeq(accessToken);
        }
        return memSeq;
    }

    public String getMemIdFromHttpServletRequest(HttpServletRequest httpServletRequest) {
        String accessToken = splitTokenFromHttpServletRequest(httpServletRequest);
        String memId = "";
        if (jwtUtil.validateToken(accessToken))
            memId = jwtUtil.getMemId(accessToken);
        return memId;
    }

    private String splitTokenFromHttpServletRequest(HttpServletRequest httpServletRequest) {
        if (httpServletRequest == null)
            throw new CustomException(CustomExceptionCode.INVALID_JWT);
        String headerValue = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (!headerValue.isEmpty())
            return headerValue.substring(7);

        throw new CustomException(CustomExceptionCode.UNSUPPORTED_JWT);
    }

}
