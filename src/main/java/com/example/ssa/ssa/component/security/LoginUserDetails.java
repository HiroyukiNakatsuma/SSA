package com.example.ssa.ssa.component.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class LoginUserDetails implements UserDetails {

    private LoginUser loginUser;
    private String mailAddress;
    private String password;

    public LoginUserDetails(LoginUser loginUser) {
        this.loginUser = loginUser;
        this.mailAddress = loginUser.getMailAddress();
        this.password = loginUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.mailAddress;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // FIX ME
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // FIX ME
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // FIX ME
    }

    @Override
    public boolean isEnabled() {
        return true; // FIX ME
    }
}
