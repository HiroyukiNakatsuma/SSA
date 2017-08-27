package com.example.ssa.ssa.domain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface PlanMapper {

    void insert(@Param("roomId") long roomId,
                @Param("title") String title,
                @Param("startDatTime") LocalDateTime startDatTime,
                @Param("endDatTime") LocalDateTime endDatTime,
                @Param("memo") String memo,
                @Param("accountId") long accountId);

}
