package com.example.ssa.ssa.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Room {
    private Long roomId;
    private String roomName;
    private Integer roomRole; // TODO Convert enums
}
