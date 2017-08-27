package com.example.ssa.ssa.component.security;

import com.example.ssa.ssa.domain.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class LoginUserDetailsService implements UserDetailsService {
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public UserDetails loadUserByUsername(String mailAddress) throws UsernameNotFoundException {
        if (mailAddress == null || "".equals(mailAddress)) {
            throw new UsernameNotFoundException("mailAddress is not null !");
        }
        
        LoginUser loginUser = accountMapper.selectAccountForLogin(mailAddress);
        if (loginUser == null) {
            throw new UsernameNotFoundException("mailAddress is not match !");
        }
        return new LoginUserDetails(loginUser);
    }
}
