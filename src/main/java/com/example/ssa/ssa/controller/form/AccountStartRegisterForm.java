package com.example.ssa.ssa.controller.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
public class AccountStartRegisterForm {
    @NotNull
    private Long accountId;
    @NotEmpty
    private String nickname;
}
