package com.fcc.PureSync.context.member.service;

import com.fcc.PureSync.context.member.entity.Member;
import com.fcc.PureSync.context.member.repository.TokenDao;
import com.fcc.PureSync.core.constant.MemberConstant;
import com.fcc.PureSync.core.exception.CustomException;
import com.fcc.PureSync.core.exception.CustomExceptionCode;
import com.fcc.PureSync.core.jwt.CustomUserDetails;
import com.fcc.PureSync.core.jwt.JwtUtil;
import com.fcc.PureSync.core.util.CreateCookie;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import static com.fcc.PureSync.core.exception.CustomExceptionCode.FAIL_CREATE_TOKEN;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenDao tokenDao;
    @Value("${jwt.secret}")
    private String accessSecret;
    @Value("${jwt.secret2}")
    private String refreshSecret;
    @Value("${jwt.token-validity-in-seconds}")
    private long accessTokenValidityInSecond;
    @Value("${jwt.token-validity-in-seconds2}")
    private long refreshTokenValidityInSeconds;


    //AccessToken과 RefreshToken의 생성
    public HashMap<String, Object> createToken(Member member, HttpServletResponse response) {
        String jti = UUID.randomUUID().toString();
        String accessToken = createAccessToken(member, jti);
        String refreshToken = createRefreshToken(member);
        saveRefreshToken(jti, refreshToken, refreshTokenValidityInSeconds * 1000);
        CreateCookie.addCookie(refreshToken, response);
        return publicAccessToken(accessToken);
    }

    //AccessToken을 사용자에게 보내주는 메서드
    public HashMap<String, Object> publicAccessToken(String accessToken) {
        HashMap<String, Object> accessTokenMap = new HashMap<>();
        accessTokenMap.put(MemberConstant.ACCESS_TOKEN, accessToken);
        return accessTokenMap;
    }

    //AccessToken 생성
    public String createAccessToken(Member member, String jti) {
        String accessToken;
        try {
            JwtBuilder builder = Jwts.builder();
            builder.setHeaderParam("typ", "JWT"); //토큰의 종류
            builder.setHeaderParam("alg", "HS256"); //암호화 알고리즘 종류
            builder.setExpiration(new Date(new Date().getTime() + accessTokenValidityInSecond*1000));
            builder.setId(jti);
            builder.claim("memSeq", member.getMemSeq()); //토큰에 저장되는 데이터
            builder.claim("memId", member.getMemId()); //토큰에 저장되는 데이터
            builder.claim("memImg", member.getMemImg()); //토큰에 저장되는 데이터
            builder.claim("memEmail", member.getMemEmail());//토큰에 추가되는 데이터
            builder.signWith(SignatureAlgorithm.HS256, accessSecret.getBytes("UTF-8")); //비밀키
            accessToken = builder.compact(); //모든 내용을 묶기

        } catch (UnsupportedEncodingException e) {
            throw new CustomException(FAIL_CREATE_TOKEN);
        }
        return lockingToken(accessToken);
    }

    //RefreshToken 생성
    public String createRefreshToken(Member member) {
        String refreshToken;
        try {
            JwtBuilder builder = Jwts.builder();
            builder.setHeaderParam("typ", "JWT"); //토큰의 종류
            builder.setHeaderParam("alg", "HS256"); //암호화 알고리즘 종류
            builder.setExpiration(new Date(new Date().getTime() + refreshTokenValidityInSeconds*1000));
            builder.signWith(SignatureAlgorithm.HS256, accessSecret.getBytes("UTF-8")); //비밀키
            refreshToken = builder.compact(); //모든 내용을 묶기
        } catch (UnsupportedEncodingException e) {
            throw new CustomException(FAIL_CREATE_TOKEN);
        }
        return lockingToken(refreshToken);
    }


    //RefreshToken을 Redis에 저장
    public void saveRefreshToken(String accessTokenUUID, String refreshToken, Long refreshTokenValidityInMilliseconds) {
        tokenDao.saveRefreshToken(accessTokenUUID, refreshToken, refreshTokenValidityInMilliseconds);
    }
    //리프레시 토큰 비교



    public void afterLoginValidCustomUserDetails(CustomUserDetails customUserDetails) {
        if (customUserDetails == null) {
            throw new CustomException(CustomExceptionCode.INVALID_JWT);
        }
        LocalDateTime lastLoginTime = customUserDetails.getExpirationTime();
        Duration validityInSecondAccessTokenTime = Duration.ofSeconds(accessTokenValidityInSecond);
        LocalDateTime expirationAccessTokenTime = lastLoginTime.plus(validityInSecondAccessTokenTime);
        LocalDateTime nowTime = lastLoginTime.now();
        Duration remainingTime = Duration.between(nowTime,expirationAccessTokenTime);
        if(remainingTime.toMinutes()<5){

        }
//            reissueAccessToken(accessTokenUUID)
    }

    //AccessToken 재발급
    public void reissueAccessToken(String accessTokenUUID) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Member member = userDetails.getMember();
        String accessToken = createAccessToken(member, accessTokenUUID);
        publicAccessToken(accessToken);
    }



    public String lockingToken(String token) {
        StringBuffer accessToken = new StringBuffer(token);
        accessToken.insert(58, "Spu935");
        accessToken.insert(77, "Bus9712");
        accessToken.insert(122, "9YrH7");
        accessToken.insert(199, "01Kej11");
        String lockToken = accessToken.toString();
        return lockToken;
    }

    public Claims getClaims(String token) {
        Claims claims;
        try {
            JwtParser parser = Jwts.parser();
            parser.setSigningKey(accessSecret.getBytes("UTF-8"));
            Jws<Claims> jws = parser.parseClaimsJws(token);
            claims = jws.getBody();
        } catch (Exception e) {
            throw new CustomException(FAIL_CREATE_TOKEN);
        }
        return claims;
    }

    //JWT 토큰에서 사용자 아이디 얻기
    public String getMemId(String token) {
        String id;
        try {
            JwtParser parser = Jwts.parser();
            parser.setSigningKey(accessSecret.getBytes("UTF-8"));
            Jws<Claims> jws = parser.parseClaimsJws(token);
            Claims claims = jws.getBody();
            id = claims.get("memId", String.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(FAIL_CREATE_TOKEN);
        }
        return id;
    }

    public Long getMemSeq(String token) {
        Long memSeq = null;
        try {
            JwtParser parser = Jwts.parser();
            parser.setSigningKey(accessSecret.getBytes("UTF-8"));
            Jws<Claims> jws = parser.parseClaimsJws(token);
            Claims claims = jws.getBody();
            memSeq = claims.get("memSeq", Long.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(FAIL_CREATE_TOKEN);
        }
        return memSeq;
    }

    public String getMemImg(String token) {
        String memImg;
        try {
            JwtParser parser = Jwts.parser();
            parser.setSigningKey(accessSecret.getBytes("UTF-8"));
            Jws<Claims> jws = parser.parseClaimsJws(token);
            Claims claims = jws.getBody();
            memImg = claims.get("memImg", String.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(FAIL_CREATE_TOKEN);
        }
        return memImg;
    }


}
