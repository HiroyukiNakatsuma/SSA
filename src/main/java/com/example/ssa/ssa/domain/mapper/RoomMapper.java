package com.example.ssa.ssa.domain.mapper;

import com.example.ssa.ssa.domain.model.Room;
import com.example.ssa.ssa.domain.model.RoomDetail;
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
