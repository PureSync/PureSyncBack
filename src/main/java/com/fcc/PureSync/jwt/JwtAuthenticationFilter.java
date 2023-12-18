package com.fcc.PureSync.jwt;

import com.fcc.PureSync.entity.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter  extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 요청에서 토큰 가져오기
            String token = parseBearerToken(request);

            // 토큰 검사하기. JWT이므로 인가 서버에 요청하지 않고도 검증 가능
            if(token != null && !token.equalsIgnoreCase(null)) {
                // userId 가져오기. 위조된 경우 예외 처리된다.
                String userId = jwtUtil.getMemId(token);
                Long memSeq = jwtUtil.getMemSeq(token);
                String memEmail = jwtUtil.getMemEmail(token);
                Member member = Member.builder().memId(userId).memSeq(memSeq).memEmail(memEmail).build();

                //인증 완료. SecurityContextHolder에 등록해야 인증된 사용자라고 생가한다.
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        new CustomUserDetails(member), // 인증된 사용자의 정보. 문자열이 아니어도 아무것이나 넣을 수 있다. 보통 UserDetails라는 오브젝트를 넣는데 우리는 넣지 않았다.
                        null,
                        AuthorityUtils.NO_AUTHORITIES
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication); // 인증 정보 넣기
                SecurityContextHolder.setContext(securityContext); // 다시 등록
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }
        finally {
            filterChain.doFilter(request, response);
        }
    }

    private String parseBearerToken(HttpServletRequest request) {
        // Http 요청의 헤더를 파싱해 Bearer 토큰을 리턴한다.
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
