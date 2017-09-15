package com.example.ssa.ssa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/album")
public class AlbumController {

    @GetMapping("/{roomId}")
    public String show(@PathVariable Long roomId,
                       Model model) {
        model.addAttribute("roomId", roomId);
        return "album/show";
    }

    @PostMapping("/{roomId}/input")
    public String imageInput() {
        return "";
    }
}
