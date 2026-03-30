package com.studyplanner.controller;

import com.studyplanner.entity.StudyPlan;
import com.studyplanner.service.StudyPlanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plan")
@CrossOrigin
public class StudyPlanController {

    private final StudyPlanService studyPlanService;

    public StudyPlanController(StudyPlanService studyPlanService) {
        this.studyPlanService = studyPlanService;
    }


    @PostMapping("/generate-multi")
    public List<StudyPlan> generateMultiDayPlan(
            @RequestParam Long userId,
            @RequestParam int dailyHours) {

        return studyPlanService.generateMultiDayPlan(userId, dailyHours);
    }

}