package com.example.ssa.ssa.controller;

import com.example.ssa.ssa.controller.form.AccountRegisterForm;
import com.example.ssa.ssa.controller.form.AccountStartRegisterForm;
import com.example.ssa.ssa.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    /**
     * 会員情報入力画面
     */
    @GetMapping("/input")
    public String input(@ModelAttribute AccountRegisterForm form) {
        return "account/input";
    }

    /**
     * 会員登録処理
     */
    @PostMapping("/register")
    public String register(@Validated AccountRegisterForm form,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "account/input";
        }
        if (accountService.isAlreadyExistsMailAddress(form.getMailAddress())) {
            model.addAttribute("mailAddressError", true);
            return "account/input";
        }

        Long accountId = accountService.register(form.getMailAddress(), form.getPassword());

        redirectAttributes.addFlashAttribute("accountId", accountId);
        return "redirect:/account/start";
    }

    /**
     * ニックネーム入力画面
     */
    @GetMapping("/start")
    public String start(@ModelAttribute AccountStartRegisterForm form) {
        return "account/start";
    }

    /**
     * ニックネーム登録処理
     */
    @PostMapping("/startRegister")
    public String startRegister(@Validated AccountStartRegisterForm form,
                                BindingResult result) {
        if (result.hasErrors()) {
            return "account/start";
        }
        accountService.registerNickname(form.getAccountId(), form.getNickname());
        return "redirect:/";
    }

}
