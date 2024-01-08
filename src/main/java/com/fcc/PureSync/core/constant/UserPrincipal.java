package com.fcc.PureSync.core.constant;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.security.Principal;
import java.util.Collection;

@ToString
@Setter
@Getter
@NoArgsConstructor
public class UserPrincipal implements Principal {

    private  String memId;
    private  String memEmail;
    private  String memImg;
    private  Collection<? extends GrantedAuthority> authorities;



    public UserPrincipal(String memId, String memEmail, String memImg, Collection<? extends GrantedAuthority> authorities) {
        this.memId = memId;
        this.memEmail = memEmail;
        this.memImg = memImg;
        this.authorities = authorities;
    }

    public String getName() {
        return this.memId;
    }
}
