package com.example.ssa.ssa.domain;

import com.example.ssa.ssa.enums.RoomRole;
import lombok.Data;

import java.util.List;

@Data
public class RoomDetail {
    private Long roomId;
    private String roomName;
    private RoomRole roomRole;
    private List<Account> joinAccounts;
}
