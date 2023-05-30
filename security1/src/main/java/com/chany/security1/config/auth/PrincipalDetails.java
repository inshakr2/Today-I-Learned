package com.chany.security1.config.auth;


import com.chany.security1.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Security가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킴. => SecurityConfig의 loginProcessingUrl에서 지정한 주소
 * 로그인 진행이 완료가 되면 session을 만들어 줌. 일반적인 session이긴 한데, Security가 가지고 있는 session
 *                                                  => Security ContextHolder (key값)에 세션 정보를 저장함.
 * 이 떄, Security Contextholder에 들어갈 수 있는 Object는 Authentication 타입의 객체로 지정되어 있음.
 *  또한 Authentication 안에는 User 정보가 들어있어야 하는데 이 클래스도 정해져 있음.
 *   User 정보의 Object 타입은 UserDetails 타입의 객체임
 *
 *  정리하면,
 *   Security 세션 영역이 있고 여기에는 Authentication 객체로 지정되어 있음
 *   Authentication 객체 안에는 유저 정보를 저장해야하는데 여기에는 UserDetails 객체로 지정되어 있음.
 *
 */

public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    // 해당 유저의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });

        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
