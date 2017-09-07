package com.example.ssa.ssa.controller;

import com.example.ssa.ssa.component.security.LoginUserDetails;
import com.example.ssa.ssa.controller.form.RoomCreateForm;
import com.example.ssa.ssa.service.PlanService;
import com.example.ssa.ssa.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private PlanService planService;
    @Autowired
    private MessageSource messageSource;

    /**
     * 参加ルームリスト画面表示
     */
    @GetMapping("/list")
    public String list(@AuthenticationPrincipal LoginUserDetails loginUserDetails, Model model) {
        model.addAttribute("roomList", roomService.loadJoinList(loginUserDetails.getLoginUser().getAccountId()));
        return "room/list";
    }

    /**
     * ルーム作成画面表示
     */
    @GetMapping("/input")
    public String input(@ModelAttribute RoomCreateForm form) {
        return "room/input";
    }

    /**
     * ルーム作成処理
     */
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

    /**
     * ルーム詳細画面表示
     */
    @GetMapping("/detail/{roomId}")
    public String detail(@AuthenticationPrincipal LoginUserDetails loginUserDetails,
                         @PathVariable Long roomId,
                         Model model) {
        model.addAttribute("detail", roomService.loadDetail(roomId));
        try {
            model.addAttribute("calendar", planService.loadCalendarWithPlan(roomId, loginUserDetails.getLoginUser().getAccountId()));
        } catch (Exception e) {
            model.addAttribute("message", "message");
        }
        return "room/detail";
    }

    /**
     * ルーム招待リンクの生成画面表示
     */
    @PostMapping("/createInviteLink")
    public String createInviteLink(@RequestParam("roomId") Long roomId,
                                   @AuthenticationPrincipal LoginUserDetails loginUserDetails,
                                   Model model) {
        if (roomId == null || !roomService.isValid(roomId)) {
            return "room/list";
        }
        model.addAttribute("inviteLinkUrl", roomService.createInviteLink(roomId, loginUserDetails.getLoginUser().getAccountId()));
        return "room/inviteLink";
    }

    /**
     * 招待リンクからルーム参加
     */
    @GetMapping("/inviteJoin/{onetimeKey}")
    public String inviteJoin(@PathVariable String onetimeKey,
                             @AuthenticationPrincipal LoginUserDetails loginUserDetails,
                             RedirectAttributes redirectAttributes) {
        Long roomId = roomService.inviteJoin(onetimeKey, loginUserDetails.getLoginUser().getAccountId());
        if (roomId == null) {
            redirectAttributes.addFlashAttribute("invalidMessage", messageSource.getMessage("room.invite.invalid", null, Locale.getDefault()));
            return "redirect:/home/";
        }
        redirectAttributes.addFlashAttribute("roomJoinMessage", messageSource.getMessage("room.invite.join", null, Locale.getDefault()));
        return "redirect:/room/detail/" + roomId;
    }

}
