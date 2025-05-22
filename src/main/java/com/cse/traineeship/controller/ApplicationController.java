package com.cse.traineeship.controller;

import com.cse.traineeship.service.ApplicationService;
import com.cse.traineeship.service.PositionService;
import com.cse.traineeship.service.StudentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final PositionService positionService;
    private final StudentService studentService;

    public ApplicationController(ApplicationService applicationService,
                                 PositionService positionService,
                                 StudentService studentService) {
        this.applicationService = applicationService;
        this.positionService = positionService;
        this.studentService = studentService;
    }

    @GetMapping("/apply/{positionId}")
    public String showApplyForm(@PathVariable Long positionId, org.springframework.ui.Model model) {
        model.addAttribute("position", positionService.findById(positionId));
        return "student/apply-form";
    }

    @PostMapping("/apply/{positionId}")
    public String submitApply(@PathVariable Long positionId,
                              Authentication auth) {
        Long studentId = studentService
            .findByUsername(auth.getName())
            .getId();
        applicationService.apply(positionId, studentId);
        return "redirect:/positions/search";
    }
}
