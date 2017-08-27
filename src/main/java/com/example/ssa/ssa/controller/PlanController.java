package com.example.ssa.ssa.controller;

import com.example.ssa.ssa.component.security.LoginUserDetails;
import com.example.ssa.ssa.controller.form.PlanCreateForm;
import com.example.ssa.ssa.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Locale;

@Controller
@RequestMapping("/plan")
public class PlanController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private MessageSource messageSource;

    /**
     * 予定作成画面表示
     */
    @GetMapping("/input")
    public String input(@ModelAttribute PlanCreateForm form,
                        @RequestParam Long roomId,
                        @AuthenticationPrincipal LoginUserDetails loginUserDetails,
                        RedirectAttributes attributes,
                        Model model) {
        // 参加していないルームIDの場合、エラー
        if (!roomService.isJoined(loginUserDetails.getLoginUser().getAccountId(), roomId)) {
            model.addAttribute("detail", roomService.loadDetail(roomId));
            model.addAttribute("notJoinedMessage", messageSource.getMessage("plan.notJoined", null, Locale.getDefault()));
            return "room/detail";
        }
        form.setRoomId(roomId);
        form.setStartDateTime(LocalDateTime.now());
        form.setEndDateTime(LocalDateTime.now().plusHours(1));
        return "plan/input";
    }
}
