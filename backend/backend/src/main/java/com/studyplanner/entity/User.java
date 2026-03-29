package com.studyplanner.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public User() {
    }

    public User(Long id, String name, String email, int dailyStudyHours, List<Subject> subjects) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dailyStudyHours = dailyStudyHours;
        this.subjects = subjects;
    }

    private String name;
    private String email;
    private int dailyStudyHours;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Subject> subjects;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDailyStudyHours() {
        return dailyStudyHours;
    }

    public void setDailyStudyHours(int dailyStudyHours) {
        this.dailyStudyHours = dailyStudyHours;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}