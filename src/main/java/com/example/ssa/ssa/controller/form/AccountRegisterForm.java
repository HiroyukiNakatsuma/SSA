package com.example.ssa.ssa.controller.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class AccountRegisterForm {
    @NotEmpty
    private String mailAddress;
    @NotEmpty
    private String password;
}
