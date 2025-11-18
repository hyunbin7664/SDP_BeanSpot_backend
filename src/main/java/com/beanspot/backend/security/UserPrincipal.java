package com.beanspot.backend.security;

import com.beanspot.backend.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {
    private final User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    public static UserPrincipal create(User user) {
        return new UserPrincipal(
                User.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .password(user.getPassword())
                        .nickname(user.getNickname())
                        .userId(user.getUserId())
                        .phone(user.getPhone())
                        .build()
        );
    }

    public static UserPrincipal from(User user) {
        return new UserPrincipal(user);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    public Long getId(){
        return user.getId();
    }

    public String getNickname(){
        return user.getNickname();
    }

    public String getUserId(){ return user.getUserId(); }
}
