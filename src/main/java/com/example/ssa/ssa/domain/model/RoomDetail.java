package com.example.ssa.ssa.domain.model;

import com.example.ssa.ssa.domain.model.Account;
import lombok.Data;

import java.util.List;

@Data
public class RoomDetail {
    private Long roomId;
    private String roomName;
    private Integer roomRole; // TODO Convert enums
    private List<Account> joinAccounts;
}
