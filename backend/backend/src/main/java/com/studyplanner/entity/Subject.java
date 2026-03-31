package com.studyplanner.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Subject {

    public Subject(Long id, String name, int difficulty, LocalDate deadline, User user, List<StudyPlan> studyPlans) {
        this.id = id;
        this.name = name;
        this.difficulty = difficulty;
        this.deadline = deadline;
        this.user = user;
        this.studyPlans = studyPlans;
    }

    public Subject() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int difficulty;
    private LocalDate deadline;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    @JsonIgnore   // 🔥 ADD THIS
    private List<StudyPlan> studyPlans;

    // getters & setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<StudyPlan> getStudyPlans() {
        return studyPlans;
    }

    public void setStudyPlans(List<StudyPlan> studyPlans) {
        this.studyPlans = studyPlans;
    }
}