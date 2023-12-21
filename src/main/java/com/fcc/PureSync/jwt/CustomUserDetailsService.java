package com.fcc.PureSync.jwt;

import com.fcc.PureSync.common.constant.UserPrincipal;
import com.fcc.PureSync.config.UserRoleConfig;
import com.fcc.PureSync.entity.Member;
import com.fcc.PureSync.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //로그인한 아이디 값을 가지고 아이디가 존재하면 입력한 아이디의 회원정보를 db에서 불러온다.
        Member member = memberRepository.findByMemId(username).orElse(null);
        //아이디가 존재하는지 여부 확인후 입력한 비밀번호와 아이디로 불러온 회원정보의 비밀번호 값을 대조
        if(member==null)
            throw new UsernameNotFoundException(username); //아이디가 없을때 예외처리 403
        List<GrantedAuthority> authorities = new ArrayList<>();
        String userLevel = UserRoleConfig.UserRole.convertStringFromUserRole(member.getMemStatus());
        authorities.add(new SimpleGrantedAuthority(userLevel));
        UserPrincipal userPrincipal = new UserPrincipal(
                member.getMemId(),
                member.getMemEmail(),
                member.getMemImg(),
                authorities
        );
        System.out.println("userPrincipal::::::::::::::::::::::" + userPrincipal);
        CustomUserDetails userDetails = new CustomUserDetails(member,authorities,userPrincipal);
        return userDetails;
    }

}

