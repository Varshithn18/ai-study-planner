package com.studyplanner.service;

import com.studyplanner.entity.StudyPlan;
import com.studyplanner.entity.Subject;
import com.studyplanner.repository.StudyPlanRepository;
import com.studyplanner.repository.SubjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    @Transactional
    public List<StudyPlan> generateMultiDayPlan(Long userId, int dailyHours) {
        studyPlanRepository.deleteBySubject_User_Id(userId);
        List<Subject> subjects = subjectRepository.findByUserId(userId);
        LocalDate today = LocalDate.now();

        // 🔥 total hours per subject
        Map<Subject, Integer> remainingHoursMap = new HashMap<>();
        for (Subject s : subjects) {
            int totalHours = s.getDifficulty() * 2;
            remainingHoursMap.put(s, totalHours);
        }

        // max deadline
        LocalDate maxDeadline = subjects.stream()
                .map(Subject::getDeadline)
                .max(LocalDate::compareTo)
                .orElse(today);

        List<StudyPlan> finalPlan = new ArrayList<>();

        for (LocalDate currentDay = today;
             !currentDay.isAfter(maxDeadline);
             currentDay = currentDay.plusDays(1)) {

            // 🔥 STOP if all subjects completed
            if (allSubjectsCompleted(remainingHoursMap)) break;

            Map<Subject, Double> priorityMap = new HashMap<>();

            for (Subject s : subjects) {

                if (currentDay.isAfter(s.getDeadline())) continue;

                // skip completed subjects
                if (remainingHoursMap.get(s) <= 0) continue;

                long daysLeft = ChronoUnit.DAYS.between(currentDay, s.getDeadline());
                if (daysLeft <= 0) daysLeft = 1;

                double priority = (double) s.getDifficulty() / daysLeft;
                priorityMap.put(s, priority);
            }

            List<Subject> sortedSubjects = new ArrayList<>(priorityMap.keySet());
            sortedSubjects.sort((a, b) ->
                    Double.compare(priorityMap.get(b), priorityMap.get(a)));

            int remainingHours = dailyHours;

            while (remainingHours > 0 && !sortedSubjects.isEmpty()) {

                boolean allocated = false;

                for (Subject s : sortedSubjects) {

                    if (remainingHours == 0) break;

                    if (remainingHoursMap.get(s) <= 0) continue;

                    StudyPlan plan = new StudyPlan();
                    plan.setSubject(s);
                    plan.setDate(currentDay);
                    plan.setHoursAllocated(1);
                    plan.setStatus("PLANNED");

                    finalPlan.add(plan);

                    remainingHours--;
                    remainingHoursMap.put(s, remainingHoursMap.get(s) - 1);

                    allocated = true;
                }

                if (!allocated) break;
            }
        }

        return studyPlanRepository.saveAll(finalPlan);
    }

    // 🔥 helper method
    private boolean allSubjectsCompleted(Map<Subject, Integer> map) {
        for (int val : map.values()) {
            if (val > 0) return false;
        }
        return true;
    }
}