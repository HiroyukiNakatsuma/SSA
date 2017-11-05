package com.example.ssa.ssa.controller;

import com.example.ssa.ssa.component.security.LoginUser;
import com.example.ssa.ssa.component.security.LoginUserDetails;
import com.example.ssa.ssa.controller.form.PlanCreateForm;
import com.example.ssa.ssa.exception.BadRequestException;
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
                        @RequestParam Long roomId) {
        form.setRoomId(roomId);
        form.setStartDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        form.setStartTime(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        form.setEndDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        form.setEndTime(LocalTime.now().plusHours(1).format(DateTimeFormatter.ofPattern("HH:mm")));
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
        // 開始日時より終了日時の方が早い場合、エラー
        // TODO フォームバリデーションに移管
        if (LocalDateTime.parse(form.getStartDate().concat(" ").concat(form.getStartTime()), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .isAfter(LocalDateTime.parse(form.getEndDate().concat(" ").concat(form.getEndTime()), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))) {
            model.addAttribute("dateErrorMessage", messageSource.getMessage("plan.dateError", null, Locale.getDefault()));
            return "plan/input";
        }
        try {
            planService.create(
                    form.getRoomId(),
                    form.getTitle(),
                    LocalDate.parse(form.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalTime.parse(form.getStartTime(), DateTimeFormatter.ofPattern("HH:mm:ss")),
                    LocalDate.parse(form.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalTime.parse(form.getEndTime(), DateTimeFormatter.ofPattern("HH:mm:ss")),
                    form.getMemo(),
                    loginUser.getAccountId());
        } catch (Exception e) {
            return "plan/input";
        }
        model.addAttribute("successMessage", messageSource.getMessage("plan.create.success", null, Locale.getDefault()));
        return "redirect:/room/detail/" + form.getRoomId();
    }

    /**
     * 予定一覧画面表示
     */
    @GetMapping("/list/{roomId}")
    public String list(@PathVariable Long roomId,
                       @RequestParam String targetDate,
                       @AuthenticationPrincipal LoginUserDetails loginUserDetails,
                       Model model) {
        if (!roomService.isJoined(loginUserDetails.getLoginUser().getAccountId(), roomId)) {
            return "plan/input";
        }
        model.addAttribute("planList", planService.loadListOfDate(roomId, LocalDate.parse(targetDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        model.addAttribute("targetDate", targetDate);
        return "plan/list";
    }

    /**
     * 予定詳細画面表示
     */
    @GetMapping("/detail/{planId}")
    public String detail(@PathVariable long planId,
                         @AuthenticationPrincipal LoginUserDetails loginUserDetails,
                         Model model) {
        if (!planService.isValid(planId, loginUserDetails.getLoginUser().getAccountId())) {
            throw new BadRequestException();
        }
        model.addAttribute("detail", planService.loadDetail(planId));
        return "plan/detail";
    }
}
