package com.cse.traineeship.controller;

import com.cse.traineeship.domain.Student;
import com.cse.traineeship.domain.User;
import com.cse.traineeship.service.StudentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping("/me/edit")
    public String editMyProfile(Authentication authentication, Model model) {
        String username = authentication.getName();
        Student student = studentService.findByUsername(username);
        if (student == null) {
            // Δημιουργούμε ένα προσωρινό Student για να μην έχουμε null fields
            student = new Student();
            student.setUsername(username);
            student.setFullName("");
            student.setUniversityId("");
            student.setPreferredLocation("");
            student.setInterests(List.of());   // κενές λίστες
            student.setSkills(List.of());
        }
        model.addAttribute("student", student);
        return "student/student-profile-form";
    }


    /** Υποβολή φόρμας για create OR update */
    @PostMapping("/me/edit")
    public String saveMyProfile(Authentication authentication,
                                @RequestParam String fullName,
                                @RequestParam String universityId,
                                @RequestParam String preferredLocation,
                                @RequestParam String interests,
                                @RequestParam String skills) {

        String username = authentication.getName();

        List<String> interestList = Arrays.stream(interests.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        List<String> skillList = Arrays.stream(skills.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        Student existing = studentService.findByUsername(username);
        if (existing == null) {
            // δημιουργία νέου
            studentService.createProfile(
                    username, fullName, universityId, preferredLocation, interestList, skillList
            );
        } else {
            // επεξεργασία υπάρχοντος
            studentService.updateProfile(
                    existing.getId(), fullName, universityId, preferredLocation, interestList, skillList
            );
        }
        return "redirect:/";
    }
}
