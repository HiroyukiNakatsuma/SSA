package com.example.ssa.ssa.controller.form;

import com.example.ssa.ssa.component.annotation.Confirm;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@Confirm(field = "password")
public class AccountRegisterForm {
    @NotEmpty(message = "{account.mailAddress.notEmpty}")
    private String mailAddress;
    @NotEmpty(message = "{account.password.notEmpty}")
    private String password;
    @NotEmpty(message = "{account.confirmPassword.notEmpty}")
    private String confirmPassword;
}
