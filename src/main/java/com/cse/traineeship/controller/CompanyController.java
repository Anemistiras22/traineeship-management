package com.cse.traineeship.controller;

import com.cse.traineeship.domain.Company;
import com.cse.traineeship.domain.TraineeshipPosition;
import com.cse.traineeship.service.CompanyService;
import com.cse.traineeship.service.PositionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;
    private final PositionService positionService;

    public CompanyController(CompanyService companyService,
                             PositionService positionService) {
        this.companyService = companyService;
        this.positionService = positionService;
    }

    // ————————— Profile —————————

    @GetMapping("/me/edit")
    public String editMyProfile(Authentication auth, Model model) {
        String username = auth.getName();
        Company c = companyService.findByUsername(username);
        if (c == null) {
            c = new Company();
            c.setUsername(username);
        }
        model.addAttribute("company", c);
        return "company/company-profile-form";
    }

    @PostMapping("/me/edit")
    public String saveMyProfile(Authentication auth,
                                @RequestParam String name,
                                @RequestParam String location) {
        String username = auth.getName();
        Company existing = companyService.findByUsername(username);
        if (existing == null) {
            companyService.createProfile(username, name, location);
        } else {
            companyService.updateProfile(existing.getId(), name, location);
        }
        return "redirect:/company/positions";
    }

    // ————————— Dashboard Θέσεων —————————

    @GetMapping("/positions")
    public String listMyPositions(Authentication auth, Model model) {
        String username = auth.getName();
        Company c = companyService.findByUsername(username);
        // guard: αν δεν υπάρχει profile, redirect
        if (c == null || c.getName() == null || c.getName().isBlank()) {
            return "redirect:/company/me/edit";
        }

        List<TraineeshipPosition> all = c.getPositions();
        List<TraineeshipPosition> openPositions = all.stream()
                .filter(p -> p.getAssignedStudent() == null)
                .collect(Collectors.toList());
        List<TraineeshipPosition> assignedPositions = all.stream()
                .filter(p -> p.getAssignedStudent() != null)
                .collect(Collectors.toList());

        model.addAttribute("openPositions", openPositions);
        model.addAttribute("assignedPositions", assignedPositions);
        return "company/company-positions";
    }

    // ————————— US10: Νέα Θέση —————————

    @GetMapping("/positions/new")
    public String showNewPositionForm(Authentication auth, Model model) {
        String username = auth.getName();
        Company c = companyService.findByUsername(username);
        // guard: αν δεν υπάρχει profile, redirect
        if (c == null || c.getName() == null || c.getName().isBlank()) {
            return "redirect:/company/me/edit";
        }
        model.addAttribute("position", new TraineeshipPosition());
        return "company/company-position-form";
    }

    @PostMapping("/positions/new")
    public String saveNewPosition(Authentication auth,
                                  @ModelAttribute("position") TraineeshipPosition position,
                                  @RequestParam(value = "requiredSkills", required = false) String requiredSkillsCsv,
                                  @RequestParam(value = "topics", required = false) String topicsCsv) {

        String username = auth.getName();
        Company c = companyService.findByUsername(username);
        if (c == null) {
            return "redirect:/company/me/edit";
        }

        position.setCompany(c);
        List<String> reqSkills = (requiredSkillsCsv == null || requiredSkillsCsv.isBlank())
                ? List.of()
                : List.of(requiredSkillsCsv.split(",")).stream()
                .map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList());
        List<String> topics = (topicsCsv == null || topicsCsv.isBlank())
                ? List.of()
                : List.of(topicsCsv.split(",")).stream()
                .map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList());
        position.setRequiredSkills(reqSkills);
        position.setTopics(topics);

        positionService.save(position);
        return "redirect:/company/positions";
    }

    // ————————— US11: Διαγραφή Θέσης —————————

    @GetMapping("/positions/delete/{id}")
    public String deletePosition(@PathVariable Long id, Authentication auth) {
        String username = auth.getName();
        Company c = companyService.findByUsername(username);
        TraineeshipPosition pos = positionService.findById(id);
        if (pos.getCompany() != null && pos.getCompany().getId().equals(c.getId())) {
            positionService.deleteById(id);
        }
        return "redirect:/company/positions";
    }
}
