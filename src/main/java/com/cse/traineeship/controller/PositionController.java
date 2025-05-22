package com.cse.traineeship.controller;

import com.cse.traineeship.domain.Company;
import com.cse.traineeship.domain.Student;
import com.cse.traineeship.domain.TraineeshipPosition;
import com.cse.traineeship.service.CompanyService;
import com.cse.traineeship.service.PositionService;
import com.cse.traineeship.service.StudentService;
import com.cse.traineeship.service.strategy.CombinedStrategy;
import com.cse.traineeship.service.strategy.InterestBasedStrategy;
import com.cse.traineeship.service.strategy.LocationBasedStrategy;
import com.cse.traineeship.service.strategy.PositionSearchStrategy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/positions")
public class PositionController {

    private final PositionService positionService;
    private final CompanyService companyService;
    private final StudentService studentService;
    private static final double DEFAULT_THRESHOLD = 0.5;

    public PositionController(PositionService positionService,
                              CompanyService companyService,
                              StudentService studentService) {
        this.positionService = positionService;
        this.companyService = companyService;
        this.studentService = studentService;
    }

    @GetMapping
    public String list(Model model) {
        List<TraineeshipPosition> positions = positionService.findAll();
        model.addAttribute("positions", positions);
        return "positions";
    }

    @GetMapping("/search")
    public String showSearchForm(Authentication auth, Model model) {
        Student student = studentService.findByUsername(auth.getName());
        if (student == null
                || student.getFullName() == null || student.getFullName().isBlank()
                || student.getUniversityId() == null || student.getUniversityId().isBlank()) {
            return "redirect:/students/me/edit";
        }
        return "student/search-form";
    }

    @PostMapping("/search")
    public String doSearch(@RequestParam String strategy,
                           Authentication auth,
                           Model model) {
        Student student = studentService.findByUsername(auth.getName());
        if (student == null
                || student.getFullName() == null || student.getFullName().isBlank()
                || student.getUniversityId() == null || student.getUniversityId().isBlank()) {
            return "redirect:/students/me/edit";
        }

        List<TraineeshipPosition> open = positionService.findAll().stream()
                .filter(p -> p.getAssignedStudent() == null)
                .toList();

        PositionSearchStrategy strat;
        switch (strategy) {
            case "interest":
                strat = new InterestBasedStrategy(DEFAULT_THRESHOLD);
                break;
            case "location":
                strat = new LocationBasedStrategy();
                break;
            case "combined":
            default:
                strat = new CombinedStrategy(DEFAULT_THRESHOLD);
                break;
        }

        List<TraineeshipPosition> results = strat.findMatching(student, open);

        model.addAttribute("positions", results);
        return "student/search-results";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("position", new TraineeshipPosition());
        model.addAttribute("companies", companyService.findAll());
        return "company/position-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("position", positionService.findById(id));
        model.addAttribute("companies", companyService.findAll());
        return "company/position-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute TraineeshipPosition position,
                       @RequestParam("companyId") Long companyId,
                       @RequestParam(required = false) List<String> requiredSkills,
                       @RequestParam(required = false) List<String> topics) {
        Company company = companyService.findById(companyId);
        position.setCompany(company);
        position.setRequiredSkills(requiredSkills != null ? requiredSkills : List.of());
        position.setTopics(topics != null ? topics : List.of());
        positionService.save(position);
        return "redirect:/positions";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        positionService.deleteById(id);
        return "redirect:/positions";
    }

    @GetMapping("/apply/{id}")
    public String showApplyForm(@PathVariable Long id, Model model) {
        model.addAttribute("position", positionService.findById(id));
        model.addAttribute("students", studentService.findAll());
        return "student/apply-form";
    }

    @PostMapping("/apply/{id}")
    public String submitApply(@PathVariable Long id,
                              @RequestParam("studentId") Long studentId) {
        positionService.apply(id, studentId);
        return "redirect:/positions/search";
    }
}
