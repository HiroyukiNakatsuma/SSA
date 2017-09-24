package com.example.ssa.ssa.domain.mapper;

import com.example.ssa.ssa.domain.model.Photo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PhotoMapper {

    List<Photo> loadListByRoomId(@Param("roomId") long roomId);

    void insert(@Param("imagePath") String imagePath,
                @Param("roomId") long roomId,
                @Param("postedAccountId") long postedAccountId);
}
