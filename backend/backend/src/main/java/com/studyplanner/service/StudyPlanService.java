package com.studyplanner.service;

import com.studyplanner.entity.Subject;
import com.studyplanner.entity.StudyPlan;
import com.studyplanner.repository.SubjectRepository;
import com.studyplanner.repository.StudyPlanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class StudyPlanService {

    private final SubjectRepository subjectRepository;
    private final StudyPlanRepository studyPlanRepository;

    public StudyPlanService(SubjectRepository subjectRepository,
                            StudyPlanRepository studyPlanRepository) {
        this.subjectRepository = subjectRepository;
        this.studyPlanRepository = studyPlanRepository;
    }

    public List<StudyPlan> generatePlan(Long userId, int dailyHours) {

        List<Subject> subjects = subjectRepository.findByUserId(userId);

        // Step 1: calculate priority
        Map<Subject, Double> priorityMap = new HashMap<>();

        for (Subject s : subjects) {
            long daysLeft = java.time.temporal.ChronoUnit.DAYS.between(
                    LocalDate.now(), s.getDeadline());

            if (daysLeft == 0) daysLeft = 1;

            double priority = (s.getDifficulty() * 0.6) + ((1.0 / daysLeft) * 0.4);

            priorityMap.put(s, priority);
        }

        // Step 2: sort by priority (high → low)
        subjects.sort((a, b) ->
                Double.compare(priorityMap.get(b), priorityMap.get(a)));

        // Step 3: allocate hours
        List<StudyPlan> plans = new ArrayList<>();
        LocalDate date = LocalDate.now();

        for (Subject s : subjects) {
            StudyPlan plan = new StudyPlan();
            plan.setDate(date);
            plan.setSubject(s);
            plan.setHoursAllocated(dailyHours / subjects.size());
            plan.setStatus("PLANNED");

            plans.add(plan);
        }

        return studyPlanRepository.saveAll(plans);
    }
}