package com.fcc.PureSync.jwt;

import com.fcc.PureSync.entity.Member;
import com.fcc.PureSync.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import static com.fcc.PureSync.exception.CustomExceptionCode.*;


@Component
public class JwtUtil {

    private String secret;
    private long tokenValidityInMilliseconds;

    @Autowired
    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }


    // 토큰 생성
    public String createToken(Member member) {
        String token;
        try {
            JwtBuilder builder = Jwts.builder();
            builder.setHeaderParam("typ", "JWT"); //토큰의 종류
            builder.setHeaderParam("alg", "HS256"); //암호화 알고리즘 종류
            builder.setExpiration(new Date(new Date().getTime() + tokenValidityInMilliseconds));
            builder.claim("memSeq", member.getMemSeq()); //토큰에 저장되는 데이터
            builder.claim("memId", member.getMemId()); //토큰에 저장되는 데이터
            builder.claim("memImg", member.getMemImg()); //토큰에 저장되는 데이터
            builder.claim("memEmail",member.getMemEmail());//토큰에 추가되는 데이터
            builder.signWith(SignatureAlgorithm.HS256, secret.getBytes("UTF-8")); //비밀키
            token = builder.compact(); //모든 내용을 묶기
        } catch (UnsupportedEncodingException e) {
            throw new CustomException(FAIL_CREATE_TOKEN);
        }
        return token;
    }

    //JWT 토큰에서 모든 내용(Claims) 얻기
    public Claims getClaims(String token) {
        Claims claims;
        try {
            JwtParser parser = Jwts.parser();
            parser.setSigningKey(secret.getBytes("UTF-8"));
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
            parser.setSigningKey(secret.getBytes("UTF-8"));
            Jws<Claims> jws = parser.parseClaimsJws(token);
            Claims claims = jws.getBody();
            id = claims.get("memId", String.class);
        } catch (Exception e) {
            throw new CustomException(FAIL_CREATE_TOKEN);
        }
        return id;
    }
    public String getMemEmail(String token) {
        String memEmail;
        try {
            JwtParser parser = Jwts.parser();
            parser.setSigningKey(secret.getBytes("UTF-8"));
            Jws<Claims> jws = parser.parseClaimsJws(token);
            Claims claims = jws.getBody();
            memEmail = claims.get("memEmail", String.class);
        } catch (Exception e) {
            throw new CustomException(FAIL_CREATE_TOKEN);
        }
        return memEmail;
    }

    public Long getMemSeq(String token) {
        Long memSeq = null;
        try {
            JwtParser parser = Jwts.parser();
            parser.setSigningKey(secret.getBytes("UTF-8"));
            Jws<Claims> jws = parser.parseClaimsJws(token);
            Claims claims = jws.getBody();
            memSeq = claims.get("memSeq", Long.class);
        } catch (Exception e) {
            System.out.println("whwrkxek wlsWK TLqkf");
        }
        return memSeq;
    }
    public String getMemImg(String token) {
        String memImg;
        try {
            JwtParser parser = Jwts.parser();
            parser.setSigningKey(secret.getBytes("UTF-8"));
            Jws<Claims> jws = parser.parseClaimsJws(token);
            Claims claims = jws.getBody();
            memImg = claims.get("memImg", String.class);
        } catch (Exception e) {
            throw new CustomException(FAIL_CREATE_TOKEN);
        }
        return memImg;
    }

    //JWT 토큰 유효성 검사: 만료일자 확인
    public boolean validateToken(String token) {
        boolean validate = false;
        try {
            JwtParser parser = Jwts.parser();
            parser.setSigningKey(secret.getBytes("UTF-8"));
            Jws<Claims> jws = parser.parseClaimsJws(token);
            Claims claims = jws.getBody();
            validate = !claims.getExpiration().before(new Date());
			/*if(validate) {
				long remainMillSecs = claims.getExpiration().getTime() - new Date().getTime();
				logger.info("" + remainMillSecs/1000 + "초 남았음");
			}*/
        } catch (Exception e) {
            e.printStackTrace();
            //토큰 유효기간 만료에 따른 예외처리
        }
        return validate;
    }

}