package com.example.ssa.ssa.domain;

import com.example.ssa.ssa.enums.RoomRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Room {
    private Long roomId;
    private String roomName;
//    private RoomRole roomRole;
}
