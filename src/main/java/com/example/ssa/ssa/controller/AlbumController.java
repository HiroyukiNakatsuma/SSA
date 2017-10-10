package com.example.ssa.ssa.controller;

import com.example.ssa.ssa.component.security.LoginUserDetails;
import com.example.ssa.ssa.controller.form.ImageInputForm;
import com.example.ssa.ssa.exception.BadRequestException;
import com.example.ssa.ssa.service.AlbumService;
import com.example.ssa.ssa.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private RoomService roomService;

    /**
     * アルバム画面表示
     */
    @GetMapping("/{roomId}")
    public String show(@PathVariable Long roomId,
                       @AuthenticationPrincipal LoginUserDetails loginUserDetails,
                       Model model) {
        long accountId = loginUserDetails.getLoginUser().getAccountId();
        if (!(roomService.isValid(roomId) && roomService.isJoined(accountId, roomId))) {
            throw new BadRequestException();
        }
        model.addAttribute("photoList", albumService.loadList(roomId));
        model.addAttribute("roomId", roomId);
        return "album/show";
    }

    /**
     * アルバム写真投稿処理
     */
    @PostMapping("/input/{roomId}")
    public String imageInput(@Validated ImageInputForm form,
                             BindingResult result,
                             @PathVariable Long roomId,
                             @AuthenticationPrincipal LoginUserDetails loginUserDetails) {
        long accountId = loginUserDetails.getLoginUser().getAccountId();
        if (!(roomService.isValid(roomId) && roomService.isJoined(accountId, roomId))) {
            throw new BadRequestException();
        }
        if (result.hasErrors()) {
            return "album/show";
        }
        albumService.postPhoto(form.getFiles(), roomId, accountId);
        return "redirect:/album/" + roomId;
    }
}
