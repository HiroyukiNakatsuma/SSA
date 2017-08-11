package com.example.ssa.ssa.service;

import com.example.ssa.ssa.WebSecurityConfig;
import com.example.ssa.ssa.domain.Account;
import com.example.ssa.ssa.mapper.AccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AccountService {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private WebSecurityConfig webSecurityConfig;

    /**
     * アカウント情報取得
     */
    public Account loadAccount(Long accountId) {
        return accountMapper.selectById(accountId);
    }

    /**
     * メールアドレスによるアカウントの存在チェック
     */
    public boolean isExistsByMailAddress(String mailAddress) {
        return accountMapper.selectExistsByMailAddress(mailAddress);
    }

    /**
     * 新規会員登録処理
     */
    @Transactional
    public Long register(String mailAddress, String password) {
        String encodedPassword = webSecurityConfig.passwordEncoder().encode(password);
        accountMapper.insert(mailAddress, encodedPassword);
        return accountMapper.selectIdByMailAddress(mailAddress);
    }

    @Transactional
    public void startRegister(Long accountId, String nickname) {
        accountMapper.updateSetNickname(accountId, nickname);
    }

}
