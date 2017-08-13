package com.example.ssa.ssa.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OnetimeKeyRepository {

    void insert(@Param("roomId") Long roomId,
                @Param("createdAccountId") Long createdAccountId,
                @Param("randomKey") String randomKey);

}
