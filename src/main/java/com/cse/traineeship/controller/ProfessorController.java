package com.cse.traineeship.controller;

import com.cse.traineeship.domain.Professor;
import com.cse.traineeship.service.ProfessorService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/professors")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    // 1) Εμφάνιση φόρμας create/edit προφίλ
    @GetMapping("/me/edit")
    public String editMyProfile(Authentication auth, Model model) {
        String username = auth.getName();
        Professor p = professorService.findByUsername(username);
        if (p == null) {
            p = new Professor();
            p.setUsername(username);
        }
        model.addAttribute("professor", p);
        return "professor/professor-profile-form";
    }

    // 2) Υποβολή αλλαγών ή create
    @PostMapping("/me/edit")
    public String saveMyProfile(Authentication auth,
                                @RequestParam String fullName,
                                @RequestParam String interests) {
        String username = auth.getName();
        List<String> interestList = Arrays.stream(interests.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
        Professor existing = professorService.findByUsername(username);
        if (existing == null) {
            professorService.createProfile(username, fullName, interestList);
        } else {
            professorService.updateProfile(existing.getId(), fullName, interestList);
        }
        return "redirect:/professors/positions";
    }

    // 3) Λίστα Positions που επιβλέπει
    @GetMapping("/positions")
    public String listMyPositions(Authentication auth, Model model) {
        String username = auth.getName();
        Professor p = professorService.findByUsername(username);
        // αν δεν υπάρχει προφίλ, πρώτα φτιάχνουμε το προφίλ
        if (p == null) {
            return "redirect:/professors/me/edit";
        }
        // τώρα ασφαλώς p != null
        model.addAttribute("positions", p.getSupervisedPositions());
        return "professor/professor-positions";
    }
}
