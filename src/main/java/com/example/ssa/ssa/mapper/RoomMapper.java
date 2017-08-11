package com.example.ssa.ssa.mapper;

import com.example.ssa.ssa.domain.Room;
import com.example.ssa.ssa.domain.RoomDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoomMapper {

    List<Room> loadList(@Param("accountId") Long accountId);

    void insert(Room room);

    RoomDetail selectDetailById(@Param("roomId") Long roomId);

    boolean isValidById(@Param("roomId") Long roomId);
}
