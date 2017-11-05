package com.example.ssa.ssa.controller.form;

import com.example.ssa.ssa.component.annotation.Confirm;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@Confirm(field = "password")
public class AccountRegisterForm {
    @NotEmpty
    private String mailAddress;
    @NotEmpty
    private String password;
    @NotEmpty
    private String confirmPassword;
}
