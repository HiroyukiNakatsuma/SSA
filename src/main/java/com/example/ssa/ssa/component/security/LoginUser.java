package com.example.ssa.ssa.component.security;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginUser implements Serializable {
    private Long accountId;
    private String mailAddress;
    private String password;
}
