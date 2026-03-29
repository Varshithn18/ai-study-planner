package com.studyplanner.controller;

import com.studyplanner.entity.Subject;
import com.studyplanner.entity.User;
import com.studyplanner.service.SubjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
@CrossOrigin
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }


    @PostMapping
    public Subject addSubject(@RequestParam Long userId,
                              @RequestBody Subject subject) {

        User user = new User();
        user.setId(userId);

        subject.setUser(user);

        return subjectService.addSubject(subject);
    }

    @GetMapping("/user/{userId}")
    public List<Subject> getSubjectsByUser(@PathVariable Long userId) {
        return subjectService.getSubjectsByUser(userId);
    }
}