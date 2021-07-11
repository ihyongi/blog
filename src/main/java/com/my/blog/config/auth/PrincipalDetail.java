package com.my.blog.config.auth;

import com.my.blog.model.User;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
// 스프링 시큐리티의 고유한 세션저장소에 저장을 해준다.

@Getter
public class PrincipalDetail implements UserDetails { //extends를 사용하는건 상속, UserDetails 오버라이딩(alt+enter)
    private User user; // 콤포지션 (객체를 품고 있는 것)

    public PrincipalDetail(User user) {
        this.user = user;
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
    public boolean isAccountNonExpired() {//계정이 만료되지 않았는지 리턴(true:만료 안 됨)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { //계정 잠겨있는지 여부 (true:안잠겨있음)
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { //비밀번호 만료되었는지 여부 (true:만료 안 됨)
        return true;
    }

    @Override
    public boolean isEnabled() { //계정 활성화 여부 (true:활성화)
        return true;
    }

    //계정이 갖고있는 권한 목록을 리턴한다!(권한이 여러개 있을 수 있어 루프 돌리는거, 우리는 하나!)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collectors = new ArrayList<>();
        // 람다식
        collectors.add(()->{return "ROLE_"+user.getRole();});

        return collectors;
    }
}