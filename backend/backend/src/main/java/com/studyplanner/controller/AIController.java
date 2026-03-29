package com.studyplanner.controller;

import com.studyplanner.service.GeminiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
@CrossOrigin
public class AIController {

    private final GeminiService geminiService;

    public AIController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping("/recommend")
    public String getRecommendation(@RequestParam String subject) {

        String prompt = "Generate a structured study plan for " + subject +
                " including topics, time allocation, and revision strategy.";

        return geminiService.generateResponse(prompt);
    }
}