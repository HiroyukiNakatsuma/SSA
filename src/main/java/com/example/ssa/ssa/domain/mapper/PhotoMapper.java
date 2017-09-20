package com.example.ssa.ssa.domain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PhotoMapper {

    void insert(@Param("imagePath") String imagePath,
                @Param("roomId") long roomId,
                @Param("postedAccountId") long postedAccountId);
}
