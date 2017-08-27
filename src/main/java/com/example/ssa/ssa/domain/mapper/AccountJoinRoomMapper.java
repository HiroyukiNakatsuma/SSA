package com.example.ssa.ssa.domain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountJoinRoomMapper {

    void insert(@Param("accountId") Long accountId,
                @Param("roomId") Long roomId,
                @Param("roomRole") Integer roomRole);

    boolean isJoined(@Param("accountId") Long accountId,
                     @Param("roomId") Long roomId);
}
