package com.fcc.PureSync.jwt;

import com.fcc.PureSync.entity.Member;
import com.fcc.PureSync.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //로그인한 아이디 값을 가지고 아이디가 존재하면 입력한 아이디의 회원정보를 db에서 불러온다.
        Member member = memberRepository.findByMemId(username).orElse(null);
        //아이디가 존재하는지 여부 확인후 입력한 비밀번호와 아이디로 불러온 회원정보의 비밀번호 값을 대조
        if(member == null) {
            throw new UsernameNotFoundException(username); //아이디가 없을때 예외처리 403
//            throw new ErrorException(HttpStatus.UNAUTHORIZED.value(), "아이디가 없다."); //401
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
//      authorities.add(new SimpleGrantedAuthority(member.getRole())); //회원정보에서 권한정보를 가져와서 담는다.
        CustomUserDetails userDetails = new CustomUserDetails(member);
        return userDetails;
    }
}

