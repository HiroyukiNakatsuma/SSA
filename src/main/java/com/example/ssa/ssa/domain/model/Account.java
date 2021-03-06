package com.example.ssa.ssa.domain.model;

import lombok.Data;

@Data
public class Account {
    private Long accountId;
    private String mailAddress;
    private String password;
    private String nickname;
}
