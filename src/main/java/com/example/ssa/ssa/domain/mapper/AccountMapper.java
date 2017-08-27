package com.example.ssa.ssa.domain.mapper;

import com.example.ssa.ssa.component.security.LoginUser;
import com.example.ssa.ssa.domain.model.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountMapper {

    LoginUser selectAccountForLogin(@Param("mailAddress") String mailAddress);

    Account selectById(@Param("accountId") Long accountId);

    boolean selectExistsByMailAddress(@Param("mailAddress") String mailAddress);

    void insert(@Param("mailAddress") String mailAddress, @Param("password") String password);

    Long selectIdByMailAddress(@Param("mailAddress") String mailAddress);

    void updateNickname(@Param("accountId") Long accountId, @Param("nickname") String nickname);

}
