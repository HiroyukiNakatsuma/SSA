package com.example.ssa.ssa.controller;

import com.example.ssa.ssa.component.security.LoginUserDetails;
import com.example.ssa.ssa.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/home")
    public String index(@AuthenticationPrincipal LoginUserDetails loginUserDetails, Model model) {
        model.addAttribute("account", accountService.loadAccount(loginUserDetails.getLoginUser().getAccountId()));
        return "home";
    }
}
