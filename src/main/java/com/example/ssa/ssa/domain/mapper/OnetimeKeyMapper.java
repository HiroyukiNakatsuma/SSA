package com.example.ssa.ssa.domain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OnetimeKeyMapper {

    void insert(@Param("roomId") Long roomId,
                @Param("createdAccountId") Long createdAccountId,
                @Param("randomKey") String randomKey);

    Long isValid(@Param("onetimeKey") String onetimeKey);

    void updateUsedFlag(@Param("onetimeKey") String onetimeKey);

}
