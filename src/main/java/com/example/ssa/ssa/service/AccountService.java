package com.example.ssa.ssa.service;

import com.example.ssa.ssa.WebSecurityConfig;
import com.example.ssa.ssa.domain.mapper.AccountMapper;
import com.example.ssa.ssa.domain.model.Account;
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
    public boolean isAlreadyExistsMailAddress(String mailAddress) {
        return accountMapper.selectExistsByMailAddress(mailAddress);
    }

    /**
     * 新規会員登録処理
     * パスワードはハッシュ化して登録する
     *
     * @param mailAddress メールアドレス
     * @param password    パスワード
     * @return
     */
    @Transactional
    public Long register(String mailAddress, String password) {
        String encodedPassword = webSecurityConfig.passwordEncoder().encode(password);
        accountMapper.insert(mailAddress, encodedPassword);
        return accountMapper.selectIdByMailAddress(mailAddress);
    }

    /**
     * ニックネーム登録
     */
    @Transactional
    public void registerNickname(Long accountId, String nickname) {
        accountMapper.updateNickname(accountId, nickname);
    }

}
