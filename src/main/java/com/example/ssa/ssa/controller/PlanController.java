package com.example.ssa.ssa.controller;

import com.example.ssa.ssa.component.security.LoginUser;
import com.example.ssa.ssa.component.security.LoginUserDetails;
import com.example.ssa.ssa.controller.form.PlanCreateForm;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Controller
@RequestMapping("/plan")
public class PlanController {
    @Autowired
    private PlanService planService;
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
                        Model model) {
        // 参加していないルームIDの場合、エラー
        if (!roomService.isJoined(loginUserDetails.getLoginUser().getAccountId(), roomId)) {
            model.addAttribute("detail", roomService.loadDetail(roomId));
            model.addAttribute("notJoinedMessage", messageSource.getMessage("plan.notJoined", null, Locale.getDefault()));
            return "room/detail";
        }
        form.setRoomId(roomId);
        form.setStartDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        form.setStartTime(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        form.setEndDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        form.setEndTime(LocalTime.now().plusHours(1).format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        return "plan/input";
    }

    /**
     * 予定作成処理
     */
    @PostMapping("/create")
    public String create(@Validated PlanCreateForm form,
                         BindingResult result,
                         @AuthenticationPrincipal LoginUserDetails loginUserDetails,
                         Model model) {
        LoginUser loginUser = loginUserDetails.getLoginUser();
        if (result.hasErrors()) {
            return "plan/input";
        }
        // 参加していないルームIDの場合、エラー
        if (!roomService.isJoined(loginUser.getAccountId(), form.getRoomId())) {
            model.addAttribute("notJoinedMessage", messageSource.getMessage("plan.notJoined", null, Locale.getDefault()));
            return "plan/input";
        }
        // 予定作成
        planService.create(
                form.getRoomId(),
                form.getTitle(),
                LocalDate.parse(form.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalTime.parse(form.getStartTime(), DateTimeFormatter.ofPattern("HH:mm:ss")),
                LocalDate.parse(form.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalTime.parse(form.getEndTime(), DateTimeFormatter.ofPattern("HH:mm:ss")),
                form.getMemo(),
                loginUser.getAccountId());
        model.addAttribute("createPlanMessage", messageSource.getMessage("plan.create.success", null, Locale.getDefault()));
        return "redirect:/room/detail/" + form.getRoomId();
    }
}
