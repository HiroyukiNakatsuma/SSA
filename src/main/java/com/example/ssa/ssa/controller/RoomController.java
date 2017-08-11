package com.example.ssa.ssa.controller;

import com.example.ssa.ssa.component.security.LoginUserDetails;
import com.example.ssa.ssa.component.util.UrlUtil;
import com.example.ssa.ssa.controller.form.RoomCreateForm;
import com.example.ssa.ssa.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Controller
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/list")
    public String list(@AuthenticationPrincipal LoginUserDetails loginUserDetails, Model model) {
        model.addAttribute("roomList", roomService.loadList(loginUserDetails.getLoginUser().getAccountId()));
        return "room/list";
    }

    @GetMapping("/input")
    public String input(@ModelAttribute RoomCreateForm form) {
        return "room/input";
    }

    @PostMapping("/create")
    public String create(@AuthenticationPrincipal LoginUserDetails loginUserDetails,
                         @Validated RoomCreateForm form,
                         BindingResult result) {
        if (result.hasErrors()) {
            return "room/input";
        }

        Long roomId = roomService.createRoom(form.getRoomName(), loginUserDetails.getLoginUser().getAccountId());

        return "redirect:/room/detail/" + roomId;
    }

    @GetMapping("/detail/{roomId}")
    public String detail(@PathVariable Long roomId, Model model) {
        model.addAttribute("detail", roomService.loadDetail(roomId));
        return "room/detail";
    }

    @PostMapping("/createInviteLink")
    public String createInviteLink(@RequestParam("roomId") Long roomId,
                                   @AuthenticationPrincipal LoginUserDetails loginUserDetails,
                                   Model model) {
        // roomIdが有効か
        if (roomId == null || !roomService.isValid(roomId)) {
            return "room/list";
        }

        // ワンタイムキーを発行してリンク生成
        model.addAttribute("inviteLinkUrl", roomService.createInviteLink());

        return "room/inviteLink";
    }

}
