package com.example.ssa.ssa.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountJoinRoomMapper {

    void insert(@Param("accountId") Long accountId,
                @Param("roomId") Long roomId);

}
