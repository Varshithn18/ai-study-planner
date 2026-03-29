package com.studyplanner.repository;

import com.studyplanner.entity.StudyPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyPlanRepository extends JpaRepository<StudyPlan, Long> {

    List<StudyPlan> findBySubjectId(Long subjectId);
}