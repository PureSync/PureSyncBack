package com.fcc.PureSync.core.jwt;

import com.fcc.PureSync.context.member.entity.Member;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fcc.PureSync.core.constant.UserPrincipal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ToString
@Getter
public class CustomUserDetails implements UserDetails {

    private Member member;
    private List<GrantedAuthority> authorities;
    private UserPrincipal userPrincipal;


    public CustomUserDetails(Member member) {
        this.member = member;
    }
    public CustomUserDetails(Member member,List<GrantedAuthority> authorities,UserPrincipal userPrincipal) {
        this.member = member;
        this.authorities= authorities;
        this.userPrincipal = userPrincipal;
    }

    @ElementCollection(fetch = FetchType.LAZY) //roles 컬렉션
//    @Builder.Default
    private List<String> roles = new ArrayList<>();



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public Long getMemSeq() {
        return member.getMemSeq();
    }
    public LocalDateTime getExpirationTime(){
        return member.getMemLastLoginAt();
    }
    @Override
    public String getPassword() {
        return member.getMemPassword();
    }

    @Override
    public String getUsername() {
        return member.getMemId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
