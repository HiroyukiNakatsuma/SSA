package com.example.ssa.ssa.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoomRole implements Convertible<Integer>, Labelable {
    MEMBER(0, "メンバー"),
    OWNER(1, "オーナー");

    private final Integer code;
    private final String label;
}
