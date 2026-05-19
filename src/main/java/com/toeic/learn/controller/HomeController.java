package com.toeic.learn.controller;

import com.toeic.learn.dto.PracticeStatsDTO;
import com.toeic.learn.service.PracticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PracticeService practiceService;

    @GetMapping("/")
    public String index(Model model) {
        PracticeStatsDTO stats = practiceService.getStats();
        model.addAttribute("stats", stats);
        return "layout/main";
    }
}
