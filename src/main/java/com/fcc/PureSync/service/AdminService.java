package com.fcc.PureSync.service;

import com.fcc.PureSync.config.UserRoleConfig;
import com.fcc.PureSync.dto.LoginDto;
import com.fcc.PureSync.entity.Member;
import com.fcc.PureSync.exception.CustomException;
import com.fcc.PureSync.exception.CustomExceptionCode;
import com.fcc.PureSync.repository.MemberRepository;
import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.SessionAttribute;

@RequiredArgsConstructor
@Service
@Transactional
public class AdminService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /*
    0.의견 1. 관리자 아이디가 한개 존재한다고 들었습니다. 그럴 때 처음부터 그것을 값 비교하면 DB를 갔다오지 않아도 됩니다.
    1.로그인 시 아이디가 없으면 예외 처리
    2.로그인 시 비밀 번호 일치하지 않으면 예외 처리
    3. 로그인 성공 시 멤버 권한 레벨이 맞지 않으면 예외 처리
    4.스프링 시큐리티에 멤버 정보 저장
    */

    public Member adminLogin(LoginDto loginDto) {
        String memId = loginDto.getMemId();
        String password = loginDto.getMemPassword();
        Member member = memberRepository.findByMemId(memId).orElseThrow(()->new CustomException(CustomExceptionCode.NOT_FOUND_USER_ID));
        if(!passwordEncoder.matches(member.getMemPassword(), password))
            throw  new CustomException(CustomExceptionCode.NOT_FOUND_USER_PW);
        if(UserRoleConfig.UserRole.ADMIN.getLevel()!=member.getMemStatus())
            throw new CustomException(CustomExceptionCode.UNAUTHORIZED_ACCESS);
        handleSecurity(memId,password);
        return member;
    }
    //관리자 아이디 - 시퀀스까지
    //
    private void handleSecurity(String memId, String password) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(memId, password);
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


}
