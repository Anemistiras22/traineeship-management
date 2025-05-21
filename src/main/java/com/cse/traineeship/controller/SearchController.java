package com.cse.traineeship.controller;

import com.cse.traineeship.domain.Student;
import com.cse.traineeship.domain.TraineeshipPosition;
import com.cse.traineeship.service.CommitteeService;
import com.cse.traineeship.service.PositionService;
import com.cse.traineeship.service.StudentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final CommitteeService committeeService;
    private final PositionService positionService;
    private final StudentService studentService;

    public SearchController(CommitteeService committeeService,
                            PositionService positionService,
                            StudentService studentService) {
        this.committeeService = committeeService;
        this.positionService = positionService;
        this.studentService = studentService;
    }

    @GetMapping
    public String showSearchForm(Model model) {
        model.addAttribute("strategies", List.of("interest", "location", "combined"));
        return "student/search-form";
    }

    @PostMapping
    public String doSearch(@RequestParam String strategy,
                           Authentication auth,
                           Model model) {
        String username = auth.getName();
        Student student = studentService.findByUsername(username);
        if (student == null) {
            // Αν δεν υπάρχει profile, πρώτα φτιάξτο
            return "redirect:/students/me/edit";
        }

        List<TraineeshipPosition> results =
                committeeService.searchPositions(student.getId(), strategy);

        model.addAttribute("positions", results);
        return "student/search-results";
    }

    @PostMapping("/apply/{positionId}")
    public String apply(@PathVariable Long positionId,
                        Authentication auth) {
        String username = auth.getName();
        Student student = studentService.findByUsername(username);
        // υποθέτουμε student != null εδώ
        positionService.apply(positionId, student.getId());
        return "redirect:/logbook";
    }
}
